package project.commands;

public class CommandWithTimer<R, P>
        implements Command<CommandWithTimer.Result<R>, P> {
    public record Result<T>(long duration, T wrapped) {
    }

    private final Command<R, P> core;

    public CommandWithTimer(Command<R, P> core) {
        this.core = core;
    }

    @Override
    public CommandWithTimer.Result<R> execute(P params) throws CommandError {
        var start = System.currentTimeMillis();
        var result = core.execute(params);
        var end = System.currentTimeMillis();
        return new Result<>(end - start, result);
    }
}