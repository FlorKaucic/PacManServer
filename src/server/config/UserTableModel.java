package server.config;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class UserTableModel extends AbstractTableModel
{
    private final Object [][] data;
    
    private final String[] columnNames = { "ID", "Usuario", "Habilitado"};
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        Integer.class, String.class, Boolean.class
    };

    private boolean [] changes;
    public UserTableModel(Object [][]data)
    {
        this.data = data;
        changes = new boolean[data.length];
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Object row = data[rowIndex][columnIndex];
        return row;
       /* 
        if(0 == columnIndex) {
            return row.getId();
        }
        else if(1 == columnIndex) {
            return row.getName();
        }
        else if(2 == columnIndex) {
            return row.getHourlyRate();
        }
        else if(3 == columnIndex) {
            return row.isPartTime();
        }
        return null;*/
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {	
    	if(columnIndex == 2)
    		return true;
    	
    	return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
       data[rowIndex][columnIndex]=aValue;
       if(columnIndex == 2){
    	   changes[rowIndex]= !changes[rowIndex];
    	   
       }
    }
    
    public boolean[] getChanges(){	
    	return changes;
    }
    
    public boolean isThereChanges(){
    	for(int i=0; i<changes.length;i++)
    		if(changes[i])
    			return true;
    	
    	return false;
    }
    public int getCantChanges(){
    	int cant=0;
    	for(int i=0; i<changes.length;i++)
    		if(changes[i])
    			cant++;
    	
    	return cant;
    }
    
    public void setChange(){
    	changes = new boolean[data.length];
    	
    }
}
