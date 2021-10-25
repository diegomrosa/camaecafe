package com.camaecafe;

public class Lang {
	private Long id;
	private String lang6391;
	private String lang6392T;
	private String lang6392B;
	private String name;
	private String nativeName;
	
	public Lang(Long id, String lang6391, String lang6392T, String lang6392B, String name, String nativeName) {
		super();
		this.id = id;
		this.lang6391 = lang6391;
		this.lang6392T = lang6392T;
		this.lang6392B = lang6392B;
		this.name = name;
		this.nativeName = nativeName;
	}

	public Long getId() {
		return id;
	}

	public String getLang6391() {
		return lang6391;
	}

	public String getLang6392T() {
		return lang6392T;
	}

	public String getLang6392B() {
		return lang6392B;
	}

	public String getName() {
		return name;
	}

	public String getNativeName() {
		return nativeName;
	}
}
