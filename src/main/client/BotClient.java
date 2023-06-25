import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int)(Math.random() * 100);
    }

    public static void main(String[] args) {
        new BotClient().run();
    }


    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws ClassNotFoundException, IOException {
            BotClient.this.sendTextMessage("Привет чату. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] array = message.split(": ");

            String format = null;
            if(array.length != 2) return;

            switch(array[1]) {
                case "дата" : format = "d.MM.YYYY";
                    break;
                case "день" : format = "d";
                    break;
                case "месяц" : format = "MMMM";
                    break;
                case "год" : format = "YYYY";
                    break;
                case "время" : format = "H:mm:ss";
                    break;
                case "час" : format = "H";
                    break;
                case  "минуты" : format = "m";
                    break;
                case "секунды" : format = "s";
            }

            if (format != null) {
                String answer = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
                BotClient.this.sendTextMessage("Информация для " + array[0] + ": " + answer);
            }
        }
    }
}