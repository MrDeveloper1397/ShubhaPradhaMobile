package task.marami.Shubhaprada.Models;

public class CollectionTransationData {
    String bookType,receiptId,pdno,name,discount,amount,bank,chequeNo,chequeDate,receiptDate,createDate;

    public CollectionTransationData(String bookType, String receiptId, String pdno, String name, String discount, String amount, String bank, String chequeNo, String chequeDate, String receiptDate, String createDate) {
        this.bookType = bookType;
        this.receiptId = receiptId;
        this.pdno = pdno;
        this.name = name;
        this.discount = discount;
        this.amount = amount;
        this.bank = bank;
        this.chequeNo = chequeNo;
        this.chequeDate = chequeDate;
        this.receiptDate = receiptDate;
        this.createDate = createDate;
    }

    public String getBookType() {
        return bookType;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getPdno() {
        return pdno;
    }

    public String getName() {
        return name;
    }

    public String getDiscount() {
        return discount;
    }

    public String getAmount() {
        return amount;
    }

    public String getBank() {
        return bank;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public String getCreateDate() {
        return createDate;
    }
}
