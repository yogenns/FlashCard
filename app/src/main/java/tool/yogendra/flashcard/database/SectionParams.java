package tool.yogendra.flashcard.database;

public class SectionParams {

    private int sectionId;
    private String sectionName;
    private String sectionColor;

    public SectionParams(int sectionId, String sectionName, String sectionColor) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.sectionColor = sectionColor;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionColor() {
        return sectionColor;
    }
}
