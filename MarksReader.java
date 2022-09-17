
/**
 * Class MarksReader consisits of certain attribute for data structure of user's informations and marks,it has several methods primiarily 
 * focued to achieve to display the csv file data,calculating its each total marks, listing out 10 highest and lowest marks as per totalmarks,
 * filtering the marks below certain threshold value and menu system to view all these seperate functionality.
 *
 * @author (Sagar Sigdel)
 * @version (16/09/2022)
 */
import java.io.File;  
import java.util.*;  
public class MarksReader
{
    private String fullName;
    private int id;
    private double a1,a2,a3; //represents assignment 1's,2's,and 3's marks respectively;
    private static String unitName="Fundamentals of Programming";
    public MarksReader(String firstName,String lastName,int id,double a1, double a2,double a3){
        this.fullName = firstName +" "+ lastName;
        this.id = id;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }
    
    public double getTotal(){
        return a1+a2+a3;
    }
    
    public String getUnitName(){
        return unitName;
    }
    
    public String toString() {
        double total = getTotal();
        return String.format(
            "id:%d, fullname: %s, A1:%.2f, A2: %.2f, A3: %.2f, Total: %.2f",
            id,fullName, a1, a2, a3,total);  
    } 
    
    public String toRawString() {
        return String.format(
            "id:%d, fullname: %s, A1:%.2f, A2: %.2f, A3: %.2f",
            id,fullName, a1, a2, a3);  
    }
    
    public static Comparator<MarksReader> ascending 
                = new Comparator<MarksReader>() {
	    public int compare(MarksReader m1, MarksReader m2) {
	      if (m1.getTotal()>m2.getTotal()){
        	      return 1;
	      }else{
        	      return -1; 
	      }
	      
	    }

	};
	
    public static Comparator<MarksReader> descending 
                          = new Comparator<MarksReader>() {
	    public int compare(MarksReader m1, MarksReader m2) {
	      if (m1.getTotal()<m2.getTotal()){
        	      return 1;
	      }else{
        	      return -1; 
	      }
	      
	    }

	};
    public static List<MarksReader> fileReader() throws Exception{
        List<MarksReader> list = new ArrayList<MarksReader>();
        Scanner source = new Scanner(new File("./prog5001_students_grade_2022.csv")); //using relative path of the file 
        source.useDelimiter(","); //sets the delimiter pattern 
        String unit_name;
        
        while (source.hasNext())  //returns a boolean value  
        {  
            String line = source.nextLine();
            String initial_line;
            String[] dataRow;
            //["Last Name","First Name","Student ID","A1","A2","A3"]
            //trying to seperate first, second and data row respectively.
            if (line.contains("Unit")){
                initial_line = line;
            }else if(!line.contains("First Name,") ) {
                dataRow=line.split(",");
                //to emptyValue erroor handling using if stament. 4 means reamaining 2 marks is empty and so on 
                if (dataRow.length == 4){
                    list.add(new MarksReader(dataRow[0],dataRow[1],Integer.parseInt(dataRow[2]),Double.parseDouble(dataRow[3]),0.0,0.0));
                }else if(dataRow.length==5){
                    list.add(new MarksReader(dataRow[0],dataRow[1],Integer.parseInt(dataRow[2]),Double.parseDouble(dataRow[3]),Double.parseDouble(dataRow[4]),0.0));
                }else if (dataRow.length==3){
                    list.add(new MarksReader(dataRow[0],dataRow[1],Integer.parseInt(dataRow[2]),0.0,0.0,0.0));
                }else{
                    String a1=dataRow[3];
                    String a2=dataRow[4];
                    String a3=dataRow[5];
                    if (dataRow[3].isEmpty()){
                        a1="0.0";
                    }
                    if (dataRow[4].isEmpty()){
                        a2="0.0";
                    }
                    if (dataRow[5].isEmpty()){
                        a3="0.0";
                    }                    
                    list.add(new MarksReader(dataRow[0],dataRow[1],Integer.parseInt(dataRow[2]),Double.parseDouble(a1),Double.parseDouble(a2),Double.parseDouble(a3)));
                }
            }
        }   
        source.close();//close the scanner
        return list;
    }
    public static void main(String[] args) throws Exception {
        List<MarksReader> list = new ArrayList<MarksReader>();
        list = fileReader();
        Scanner scanObj = new Scanner(System.in);
        System.out.println("Select the menu"+
                            "\n 1. Enter 1 to display student information and assigment marks"+
                            "\n 2. Enter 2 to display total marks of all students assignment"+
                            "\n 3. Enter 3 to display the list of students with the total marks less than a certain threshold"+
                            "\n 4. Enter 4 to display the 10 highest and the 10 lowest Student marks"+
                            "\n 5. Enter 0 to exit the menu\n");
        
        int choice_id;
        
        do{
            choice_id = scanObj.nextInt();
            
            switch (choice_id) {
              case 1: 
                System.out.println("Student informations and Marks");
                System.out.println(list.get(0).unitName);
                for (MarksReader l:list){
                        System.out.println(l.toRawString());
                } 
                break;
              case 2:
                System.out.println("Total Marks");
                arrayData(list,list.size());
                break;
              case 3:
                  float threshold;
                  System.out.println("Enter threshold of totalmarks");
                  threshold = scanObj.nextFloat();
                  MarksReader[] filtered_list = list.stream().filter(p -> p.getTotal() < threshold).toArray(MarksReader[]::new);
                  System.out.println("Data with threshold is :");
                  printArray(filtered_list);
                break;
              case 4:
                Collections.sort(list,ascending);
                System.out.println("Ascending...");
                arrayData(list,10);
                Collections.sort(list,descending);
                System.out.println("Descending...");
                arrayData(list,10);
                break;
                
            }
        }while(choice_id!=0);   
    }  
    
    public static void arrayData(List<MarksReader> list,int size){
        System.out.println();
        for (int j=0;j<size;j++){
            System.out.println(list.get(j).toString());
        }    
    }
    
    public static void printArray(MarksReader[] list){
        for (MarksReader l:list){
            System.out.println(l.toString());
        }        
    }
    
}
