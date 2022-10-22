package models;

public class Table {
	private String id;
	private String name;

	public Table(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return id + ", " + name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
