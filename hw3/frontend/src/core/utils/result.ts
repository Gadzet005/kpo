export type Result<T, E = unknown> =
    | {
          success: true;
          data: T;
      }
    | {
          success: false;
          code: string;
          error?: E;
      };

export function success<T>(data: T): Result<T> {
    return {
        success: true,
        data,
    };
}

export function error<E>(code: string, error?: E): Result<never, E> {
    return {
        success: false,
        code,
        error,
    };
}
