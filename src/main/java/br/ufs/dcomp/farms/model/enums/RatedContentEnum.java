package br.ufs.dcomp.farms.model.enums;

public enum RatedContentEnum {

	TITLE("0", "Title"), TITLE_ABSTRACT("1", "Title + Abstract"), TITLE_ABSTRACT_INTRODUCTION("2",
			"Title + Abstract + Introduction"), TITLE_ABSTRACT_INTRODUCTION_CONCLUSION("3",
					"Title + Abstract + Introduction + Conclusion"), FULL_TEXT("4", "Full Text");

	private String code;
	private String description;

	private RatedContentEnum(String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static RatedContentEnum fromCode(String code) {
		for (RatedContentEnum e : RatedContentEnum.values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		return null;
	}

	public static RatedContentEnum fromDescription(String description) {
		if (description == null) {
			return null;
		}
		for (RatedContentEnum e : RatedContentEnum.values()) {
			if (description.equals(e.toString()) || description.equals(e.name())) {
				return e;
			}
		}
		return null;
	}
}