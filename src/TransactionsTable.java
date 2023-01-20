import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

//Table for transactions
public class TransactionsTable extends AbstractTableModel {
    public List<Object[]> transactions = new ArrayList<>();
    private final String[] columns = {"Type", "Amount", "Date"};

    public void addTransaction(String type, String amount, String date, String currency) {
        Object[] newTransaction = new Object[3];
        newTransaction[0] = type;
        newTransaction[1] = amount + " " + currency;
        newTransaction[2] = date;
        transactions.add(newTransaction);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return transactions.get(0).length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return transactions.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}