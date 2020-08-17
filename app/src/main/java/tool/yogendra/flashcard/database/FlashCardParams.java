package tool.yogendra.flashcard.database;

public class FlashCardParams {

    private int flashCardId;
    private String flashCardFront;
    private String flashCardBack;
    private String flashCardSectionId;

    public FlashCardParams(int flashCardId, String flashCardFront, String flashCardBack, String flashCardSectionId) {
        this.flashCardId = flashCardId;
        this.flashCardFront = flashCardFront;
        this.flashCardBack = flashCardBack;
        this.flashCardSectionId = flashCardSectionId;
    }


}
