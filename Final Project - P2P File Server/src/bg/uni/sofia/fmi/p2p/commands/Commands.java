package bg.uni.sofia.fmi.p2p.commands;

public enum Commands {
	QUIT(-1), FETCH_USERS(0), REGISTER(1), UNREGISTER(2), LIST_FILES(3);

	private final int command;

	private Commands(int command) {
		this.command = command;
	}

	public int getCommand() {
		return command;
	}
}
