public class Application {
    public static void main( String args[] ){
        new Thread(){
            @Override
            public void run() {
                new ServerProgram();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                new ClientProgram();
            }
        }.start();
    }
}
