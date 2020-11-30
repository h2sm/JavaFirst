import java.util.*;

public class third {
    public static void main(String[] args) throws NoClientsException, NoSuchClient, TooManyDeposits {
        BankManagement open = new BankManagement();
        open.loadClients();
        open.test();
    }
}
class BankManagement{
    Bank mainBank = new Bank();
    void loadClients() throws TooManyDeposits {//загружаем изначальный массив клиентов
        mainBank.addClient("Евгений Бланов");
        mainBank.addClient("Сосиска Наталья Витальевна");
        mainBank.addClient("Борис Олегович Ганалов");
        mainBank.addClient("Шмаль Анна Николаевна");
        mainBank.addClient("Уланов Евгений Бок");
        mainBank.addClient("Магомедов Зорро");
        mainBank.addClient("Россия Беспутина");
        mainBank.addClient("Казимир Кукуруза");
        mainBank.addClient("Василий");
    }
    String clientBD(){
        String [] clientBD = new String[]{"Головач Лена", "Гена Кабукин", "Иван Болван", "Сэрпиво Эдуард", "Кровопийца Тамара", "Кальян Наталья"};
        return clientBD[new Random().nextInt(clientBD.length)];
    }
    void test() throws NoClientsException, NoSuchClient, TooManyDeposits {
        Random x = new Random();//рандом мой рандом....
        for (int i=0; i<24*365;i+=x.nextInt(120)+1){
            int clientID = x.nextInt(mainBank.getCountOfClients());
            int numOfDep = x.nextInt(5);
            int sum = x.nextInt(5553535);
            int sum1 = x.nextInt(1000);
            MoneyStack clientMoneyStack = new MoneyStack(new int[] {sum1,sum1,sum1,sum1,sum1,sum1,sum1});//2587 Убитых Енотов - рандомный moneystack

            if(i%(30*24)==0 && i!=0){//начисление процентов каждый месяц
                System.out.println("------ПЕРЕРАСЧЕТ ПРОЦЕНТИКОВ------");
                mainBank.addMonthlyRate();
            }

            switch (x.nextInt(6)) {
                case 0 -> {
                    try {
                       mainBank.addMoneyToDeposit(clientID, numOfDep, clientMoneyStack);
                       System.out.println("Клиент " + mainBank.getName(clientID) + " добавил " + clientMoneyStack.moneyStackToIntTransition(clientMoneyStack) + " на счет " + numOfDep +". Баланс: "+ mainBank.showMoneyOnAllDeps(clientID));
                    } catch (IllegalDepositException e) {
                        e.printStackTrace();
                    }
                }
                case 1 ->{//ужалить клиента
                    String name = mainBank.getName(clientID);
                    try{
                        mainBank.deleteClient(clientID);
                    }
                    catch (NoSuchClient | IllegalDepositException e){
                        e.printStackTrace();
                    }
                    System.out.println("Клиент " + name + " удален");

                }
                case 2 -> {
                    try {
                        mainBank.deleteOneDeposit(clientID, numOfDep);//удалить депозит
                        System.out.println("Клиент " +mainBank.getName(clientID) + " удалил депозит" + numOfDep);
                    }
                    catch (IllegalDepositException e){
                        e.printStackTrace();
                    }
                }
                case 3 ->{
                    try {
                        mainBank.openNewDeposit(clientID);//открыть депозит
                        System.out.println("Клиент " +mainBank.getName(clientID) +  " открыл депозит "  +  ". Остаток: " + mainBank.showMoneyOnAllDeps(clientID));
                    }
                    catch (TooManyDeposits | IllegalDepositException e){
                        e.printStackTrace();
                    }
                }
                case 4 ->{
                    try {
                        mainBank.withdrawMoneyFromDeposit(clientID,numOfDep,sum);//снять деньги со счета
                        System.out.println("Клиент" + mainBank.getName(clientID) + " снял " + sum + " со счета " + numOfDep +". Баланс: "+ mainBank.showMoneyOnAllDeps(clientID));
                    }
                    catch (IllegalDepositException | NotEnoughBanknotesException e){
                        e.printStackTrace();
                    }
                }
                case 5->{
                    try {
                        mainBank.addClient(clientBD());//добавить клиента
                    }
                    catch (TooManyDeposits e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class Bank{
    private ArrayList<String> listOfClients = new ArrayList<>();//список клиентов
    final MoneyStack bankCapit = new MoneyStack(new int[] {10000000,1000000,1000000,1000000,100000,100000,1000000});//количество банкнот каждого номинала
    private HashMap<String, Client> map = new HashMap<>() ;
    public ArrayList<String> getListOfClients() {
        return listOfClients;
    }
    public void setListOfClients(ArrayList<String> listOfClients) {
        this.listOfClients = listOfClients;
    }
    public MoneyStack getBankCapit() {
        return bankCapit;
    }

    String getName(int ID) throws NoSuchClient {
        String name;
        try{
            name = listOfClients.get(ID);
        }
        catch (IndexOutOfBoundsException e){
            throw new NoSuchClient("Нет такого клиента");
            }
        return name;
    }
    void addClient(String nameOfClient) throws TooManyDeposits {//добавление клиента
        if(listOfClients.contains(nameOfClient)){//проверка на повторное добавление
            System.out.println("Клиент " + nameOfClient+ " уже есть");
        }
        else{
            listOfClients.add(nameOfClient);//добавляем клиента если его не было
            map.put(nameOfClient, new Client(nameOfClient));
            map.get(nameOfClient).addDeposit();
            System.out.println(nameOfClient + " добавлен");
        }
    }
    void deleteClient(int ID) throws NoSuchClient, IllegalDepositException {//процедура удаления клиента + ПЕРЕДАВАЙ Client
        Object k;
        try{
            k = listOfClients.get(ID);
        }
        catch (IndexOutOfBoundsException e){
            throw new IllegalDepositException("Клиента с данным ID " + ID + " не существует");
        }
        String name = getName(ID);
        Client x = map.get(listOfClients.get(ID));
        x.deleteAllDeposits();//удаляем все его депозиты
        listOfClients.remove(ID);//удаляем клиента из списков клиента
        map.remove(name);
        //System.out.println(listOfClients + "listOfClients --- " + map + "map---- "  );
    }
    void deleteOneDeposit(int ID, int numOfDep) throws NoSuchClient, IllegalDepositException {//процедура удаления определенного депозита
        Object k;
        try{
            k = listOfClients.get(ID);
        }
        catch (IndexOutOfBoundsException e){
            throw new NoSuchClient("Удаление депозиита невозможно, нет клиента с ID " + ID + ". Массив: " + listOfClients);
        }
        Client x =map.get(k);
        x.deleteDeposit(numOfDep);
    }
    void openNewDeposit(int ID) throws TooManyDeposits, IllegalDepositException {//открытие депозита
        Object k;
        try {
            k = listOfClients.get(ID);
        }
        catch (IndexOutOfBoundsException e){
            throw new IllegalDepositException("Открыть депозит не удалось. ");
        }
        Client x =map.get(k);
        x.addDeposit();
    }
    String showMoneyOnAllDeps(int ID){//показать сумму всех депозитов
        Client x =map.get(listOfClients.get(ID));
        return x.showSumOnEachDeposit().toString();
    }
    void addMoneyToDeposit(int ID, int numOfDep, MoneyStack clientMoneyStack) throws IllegalDepositException {//добавить денег на определенный депозит
        Object k;
        try {
            k = listOfClients.get(ID);
        }
        catch (IndexOutOfBoundsException e){
            throw new IllegalDepositException("Невозможно зачислить средства ");
        }
        Client x =map.get(k);
        x.addSumOnDeposit(clientMoneyStack,numOfDep);
        bankCapit.acceptMoneyStack(clientMoneyStack);

    }
    void withdrawMoneyFromDeposit(int ID, int numOfDep, int sum) throws IllegalDepositException, NotEnoughBanknotesException {//снять денег с определенного депозита
        Client x = map.get(listOfClients.get(ID));
        x.withdrawMoneyFromDeposit(sum,numOfDep);
        bankCapit.selectMoneyStackBy(sum);
    }
    void addMonthlyRate() throws NoClientsException, NoSuchClient {
        for (int i = 0; i< getCountOfClients(); i++){
            Client x = map.get(listOfClients.get(i));
            x.monthRate();
        }
    }
    int getCountOfClients() throws NoClientsException {
        if (listOfClients.size() == 0){
            throw new NoClientsException("Клиентов не осталось. Рип");
        }
        else {return listOfClients.size();}
    }
}
class Client{
    private final ArrayList<Integer> deposits = new ArrayList<>();//все поля защищенные + сеттер и геттер (дописать)
    private final String clientName;
    private final int rate = 5; //ставка - 5 процентных пунктов
    Client(String name){
        this.clientName = name;
    }
    void addSumOnDeposit(MoneyStack clientMoneyStack, int numOfDeposit) throws IllegalDepositException {//добавить сумму на определенный депозит
        int summary = clientMoneyStack.moneyStackToIntTransition(clientMoneyStack);
        if (deposits.size() > numOfDeposit) {//если зачисление на счет, который еще не открыли
                deposits.set(numOfDeposit, summary + deposits.get(numOfDeposit));
            }
            else {
                throw new IllegalDepositException("Депозит " + numOfDeposit + " еще не открыт у клиента " + clientName );
            }

    }
    Integer getCountOfDeposits(){//количество депозитов клиента
        return deposits.size();
    }
    ArrayList<Integer> showSumOnEachDeposit(){//сумма на всех депозитах клиента
        return deposits;
    }
    int showSumOnOneDeposit(int numOfDeposit) throws IllegalDepositException {//сумма денег на определенном депозите
        if (deposits.size() <= numOfDeposit) {
            throw new IllegalDepositException("Депозит " + numOfDeposit + " еще не открыт у клиента " + clientName );
        }
        else {
            return deposits.get(numOfDeposit);
        }
    }
    void withdrawMoneyFromDeposit(int sum, int numOfDeposit) throws IllegalDepositException, NotEnoughBanknotesException {//снять сумму с определенного депозита
        //если денег для снятия достаточно как у банка, так и у клиента
        if (showSumOnOneDeposit(numOfDeposit) > sum) {
            deposits.set(numOfDeposit, showSumOnOneDeposit(numOfDeposit) - sum);
        }
        else {
            throw new NotEnoughBanknotesException("У клиента " + clientName + " недостаточно средств для списания");
        }
    }
    void addDeposit() throws TooManyDeposits {//открытие нового депозита
        if(deposits.size() < 4){
            deposits.add(0);
        }
        else{
            throw new TooManyDeposits("Клиент " + clientName + " достиг порога депозитов" );
        }
    }
    void deleteDeposit(int numOfDeposit) throws IllegalDepositException {//удаляем отдельно взятый депозит
        if (deposits.size() > numOfDeposit){
                deposits.remove(numOfDeposit);
            }
            else {
                throw new IllegalDepositException("Депозит  "+ numOfDeposit + " У клиента " + clientName + " не был удален: его нет " + showSumOnEachDeposit());
        }
    }
    void deleteAllDeposits(){//удаляем все депозиты
        deposits.clear();//удаляй его вася
    }
    void monthRate(){//пересчет вкладов с учетом процента rate;
        int x,y=0;
        for (int i = 0; i<deposits.size(); i++){
            x = deposits.get(i);//получаем значение из депозита
            int save = x;
            y = x*rate/100;//высчитываем процент
            x+=y;
            System.out.println(save+ " -->" +x);
            deposits.set(i,x);
        }
    }
}
class MoneyStack{//когда клиент вносит деньги - вызываем класс
    public static final int COUNT = 7;//капсом пишут КОНСТАНТЫ
    private static final int NOMINALS[] = {1, 5, 10, 50, 100, 500, 1000}; //НОМИНАЛЫ
    private int amounts[] = new int[COUNT]; //КОЛИЧЕСТВО банкнот
    MoneyStack(int[] amounts){
        this.amounts = amounts;
    }

    //когда клиент забирает  - выделяет подпачки банкнот (выделять %1000, %500, %100)
    //генерировать исключения NotEnoughBanknotesException - обрабатывать исключение в switch
    //массив из у.е.
    MoneyStack selectMoneyStackBy(int sum) throws NotEnoughBanknotesException {//выдача пачки банкнот клиенту
        int newAmounts[] = new int[COUNT];
        for (int i=COUNT-1; i>=0;i--){
            while (sum>=NOMINALS[i] && amounts[i]>0){
                newAmounts[i]++;
                amounts[i]--;
                sum-=NOMINALS[i];
            }
        }
       if (sum==0){
           return new MoneyStack(newAmounts);
       }
       else {
           throw new NotEnoughBanknotesException("Денег нет, но вы держитесь");
       }
    }
    void acceptMoneyStack(MoneyStack moneyStack){//принятие пачки банкнот и добавление ее в капитал банка
        for (int i = COUNT-1; i>=0;i--){
            while (moneyStack.amounts[i] >=NOMINALS[i] && amounts[i]>0){
                moneyStack.amounts[i]--;
                amounts[i]++;
            }
        }
    }
    int moneyStackToIntTransition(MoneyStack moneyStack){//преобразовать MoneyStack в сумму
        int sum=0;
        for (int i = 0; i<COUNT; i++){
            sum+=amounts[i] * NOMINALS[i];
        }
        return sum;
    }
}
class BankException extends Exception{
    //исключения наследуются через этот класс
    BankException(String msg){
        super(msg);
    }
}
class NotEnoughBanknotesException extends BankException{
    NotEnoughBanknotesException(String msg){
        super(msg);
    }
}
class ClientException extends Exception{
    ClientException(String msg){super(msg);}
}
class IllegalDepositException extends ClientException{
    IllegalDepositException(String msg){
        super(msg);
    }
}
class BankEx extends Exception{
    BankEx(String msg){super(msg);}
}
class NoSuchClient extends BankEx{
    NoSuchClient(String msg){super(msg);}
}
class NoClientsException extends BankEx{
    NoClientsException(String msg){super(msg);}
}
class TooManyDeposits extends ClientException{
    TooManyDeposits(String msg){super(msg);}
}
