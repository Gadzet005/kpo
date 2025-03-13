package project.commands;

public interface Command<R, P> {
    R execute(P params) throws CommandError;
}
