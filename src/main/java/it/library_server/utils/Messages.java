package it.library_server.utils;

public interface Messages {

    String BOOK_ALREADY_EXISTS = "Bu kitob avvaldan mavjud!";
    String BOOK_DELETED = "Kitob o'chirildi.";
    String BOOK_SAVED = "Kitob saqlandi.";

    String FAVOURITE_BOOK = "Kitob yoqtirilganlarga qo'shildi.";
    String NOT_FAVOURITE_BOOK = "Kitob yoqtirilganlardan olindi.";

    String ERROR = "error :( ";
    String ERROR_OBJ = "error {}";
    String WARNING_BOOK = "bunday kitob mavjud emas";

    String SEND_NOT_COMMENT = "Siz allaqachon sharx yozib bolgansiz!";
    String ERROR_COMMENT = "Sharx yozishda xatolik roy berdi";
    String SEND_COMMENT = "Sharx muaffaqiyatli qoshildi";

}
