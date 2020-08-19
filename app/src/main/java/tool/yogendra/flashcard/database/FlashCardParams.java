package tool.yogendra.flashcard.database;

public class FlashCardParams {

    private int flashCardId;
    private String flashCardFront;
    private String flashCardBack;
    private String flashCardSectionColor;
    private String flashCardSectionName;

    public FlashCardParams(int flashCardId, String flashCardFront, String flashCardBack, String flashCardSectionColor, String flashCardSectionName) {
        this.flashCardId = flashCardId;
        this.flashCardFront = flashCardFront;
        this.flashCardBack = flashCardBack;
        this.flashCardSectionColor = flashCardSectionColor;
        this.flashCardSectionName = flashCardSectionName;
    }

    public int getFlashCardId() {
        return flashCardId;
    }

    public String getFlashCardFront() {
        return flashCardFront;
    }

    public String getFlashCardBack() {
        return flashCardBack;
    }

    public String getFlashCardSectionColor() {
        return flashCardSectionColor;
    }
}
