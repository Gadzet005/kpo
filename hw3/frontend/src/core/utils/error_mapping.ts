import { AxiosError } from "axios";
import { error, type Result } from "./result";
import { ErrorCodes } from "./error_codes";

const uknownError = error(ErrorCodes.Unknown);

type ErrorPayload<E = unknown> = {
    code: string;
    error?: E;
};

export class ErrorMapper<E = unknown> {
    private mapping: Record<number, ErrorPayload<E>>;

    constructor(mapping: Record<number, ErrorPayload<E>>) {
        this.mapping = mapping;
    }

    map(e: unknown, mustHaveError = false): Result<never, E> {
        if (e instanceof AxiosError) {
            if (e.response == null) {
                return uknownError as Result<never, E>;
            }

            const code = e.response.status;
            if (code in this.mapping) {
                const payload = this.mapping[code];
                if (mustHaveError && payload.error == undefined) {
                    return uknownError as Result<never, E>;
                }
                return error(payload.code, payload.error);
            }
            return uknownError as Result<never, E>;
        }
        return uknownError as Result<never, E>;
    }

    extend(code: number, result: ErrorPayload<E>): ErrorMapper<E> {
        const newMapper = new ErrorMapper(this.mapping);
        newMapper.mapping[code] = result;
        return newMapper;
    }
}

export function defaultErrorMapper<E = unknown>() {
    return new ErrorMapper<E>({
        400: {
            code: ErrorCodes.InvalidArguments,
        },
        404: {
            code: ErrorCodes.NotFound,
        },
        500: {
            code: ErrorCodes.InternalError,
        },
    });
}
