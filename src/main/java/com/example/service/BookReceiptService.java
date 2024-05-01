package main.java.com.example.service;

import main.java.com.example.dao.BookReceiptDao;
import main.java.com.example.model.BookBatch;
import main.java.com.example.model.BookReceipt;
import main.java.com.example.model.BookReceiptDetail;

import java.util.List;

public class BookReceiptService {

    private final BookReceiptDao bookReceiptDao;

    public BookReceiptService() {
        this.bookReceiptDao = new BookReceiptDao();
    }

    public Boolean saveBookReceiptWithDetails(BookReceipt bookReceipt, List<BookReceiptDetail> details, List<BookBatch> batchList) {
        return bookReceiptDao.saveBookReceiptWithDetails(bookReceipt, details, batchList);
    }

    public List<BookReceipt> getAllBookReceipts() {
        return bookReceiptDao.getAllBookReceipts();
    }
}
