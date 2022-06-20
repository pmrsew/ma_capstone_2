public class TestObject {

    private int variableName;
    private int variableName2;

    public TestObject(int variableName){
        this.variableName = variableName;
    }

    public void doSomething(int variableName2){


        this.variableName2 = 9;


        System.out.println("variableName should be 5 = " + variableName);
        System.out.println("this.variableName should be 5 = " + this.variableName);
        System.out.println("variableName2 should be 1 = " + variableName2);
        System.out.println("this.variableName should be 9 = " + this.variableName2);

    }
}
