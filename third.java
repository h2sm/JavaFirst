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
    void test() throws NoClientsException, NoSuchClient, TooManyDeposits {
        Random x = new Random();//рандом для свитча
        Random randID = new Random();//рандом для выделения клиента
        Random randNumOfDep = new Random();//рандом для выделения номера депозита
        Random randSum = new Random();//рандом для создания суммы
        for (int i=0; i<100;i++){
            int clientID = randID.nextInt(mainBank.getCountOfClients());
            int numOfDep = randNumOfDep.nextInt(5);
            int sum = randSum.nextInt(9999999);
            //вместо sum передавать массив с банкнотами(генерировать случайный moneystack)
            switch (x.nextInt(8)) {//выводим сообщения здесь!!!!!!
                case 0 -> {
                    try {
                        mainBank.addMoneyToDeposit(clientID, numOfDep, sum);
                    } catch (IllegalDepositException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Клиент " + mainBank.getName(clientID) + " добавил " + sum + " на счет " + numOfDep);

                }
//                case 2 ->{
//                    try{
//                        mainBank.deleteClient(clientID);
//                    }
//                    catch (NoSuchClient e){
//                        e.printStackTrace();
//                    }
//                    System.out.println("Клиент " + mainBank.getName(clientID) + " удален");
//
//                }
                case 3 -> {
                    try {
                        mainBank.deleteOneDeposit(clientID, numOfDep);
                    }
                    catch (IllegalDepositException e){
                        e.printStackTrace();
                    }
                    System.out.println("Клиент "  + " удалил депозит");
                }
                case 4 ->{
                    try {
                        mainBank.openNewDeposit(clientID);
                    }
                    catch (TooManyDeposits e){
                        e.printStackTrace();
                    }
                }
//                case 5 -> mainBank.addMonthlyRate(clientID);
                }
            }
        }
    }

class Bank{
    private ArrayList<String> listOfClients = new ArrayList<>();//список клиентов
    final MoneyStack bankCapit = new MoneyStack(new int[] {1000000000,1000000000,1000000000,1000000000,1000000000,1000000000,1000000000});
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
        if (listOfClients.contains(ID)){
            return listOfClients.get(ID);
        }
        else {
            System.out.println(listOfClients.contains(ID));
            throw new NoSuchClient("Нет запрашиваемого клиента");
        }
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
    void deleteClient(int ID) throws NoSuchClient {//процедура удаления клиента + ПЕРЕДАВАЙ Client
        Client x = map.get(listOfClients.get(ID));
        x.deleteAllDeposits();//удаляем все его депозиты
        listOfClients.remove(ID);//удаляем клиента из списков клиента
        //System.out.println(name + " удален");//здоровья погибшим
        //showMoneyOnAllDeps(ID); // для проверки
    }
    void deleteOneDeposit(int ID, int numOfDep) throws IllegalDepositException {//процедура удаления определенного депозита
        Client x =map.get(listOfClients.get(ID));
        x.deleteDeposit(numOfDep);
        System.out.println("Клиент " + listOfClients.get(ID) + " удалил депозит " + numOfDep +  ". Остаток: " + x.showSumOnEachDeposit());
    }
    void openNewDeposit(int ID) throws TooManyDeposits {//открытие депозита
        Client x =map.get(listOfClients.get(ID));
        x.addDeposit();
            System.out.println("Клиент " + listOfClients.get(ID) + " открыл депозит "  +  ". Остаток: " + x.showSumOnEachDeposit());
    }
    void showMoneyOnAllDeps(int ID){//показать сумму всех депозитов
        Client x =map.get(listOfClients.get(ID));
        System.out.println("У клиента " +listOfClients.get(ID) +" сумма на депозитах "+ x.showSumOnEachDeposit());
    }
    void addMoneyToDeposit(int ID, int numOfDep, int sum) throws IllegalDepositException {//добавить денег на определенный депозит
        Client x =map.get(listOfClients.get(ID));
        x.addSumOnDeposit(sum,numOfDep);
        try {
            bankCapit.selectMoneyStackBy(sum);
        }
        catch (NotEnoughBanknotesException e) {
            e.printStackTrace();
        }
        //System.out.println("Клиент " + listOfClients.get(ID) + " добавил денег " + sum + " на депозит " + numOfDep + ". Остаток:" + x.showSumOnEachDeposit());
    }
    void withdrawMoneyFromDeposit(int ID, int numOfDep, int sum) throws IllegalDepositException, NotEnoughBanknotesException {//снять денег с определенного депозита
        Client x = map.get(listOfClients.get(ID));
        x.withdrawMoneyFromDeposit(sum,numOfDep);
        System.out.println("Клиент " + listOfClients.get(ID) + " снял " + sum + " с депозита " + numOfDep + ". Остаток:" + x.showSumOnEachDeposit());
    }
    void addMonthlyRate(int ID){
        Client x = map.get(listOfClients.get(ID));
        x.monthRate();
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
    void addSumOnDeposit(int sum, int numOfDeposit) throws IllegalDepositException {//добавить сумму на определенный депозит
            if (deposits.size() > numOfDeposit) {//если зачисление на счет, который еще не открыли
                deposits.set(numOfDeposit, sum + deposits.get(numOfDeposit));
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

        //если недостаточно
            System.out.println("Недостаточно средств для списания");//взаимодействие консоли только через main, не в методах

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
    void capitalWithdraw(int sum) {//вычитание денег из капитала при снятии средств клиентом. возвращает тру, если капитал достаточен
            System.out.println("Дорогие друзья..... Всё! Денег нет, но вы держитесь");
            System.exit(0);//банкротство. рип вкладчик 2020-2020


    }
    void capitalAdd(int sum){//добавление денег в капитал банка

    }
    void monthRate(){
        for (int i = 0; i< deposits.size(); i++){
            int x = deposits.get(i);
            int y = x*rate/100;
            x+=y;
            deposits.set(i,x);
        }
        System.out.println("Пересчет для клиента " + clientName + ": " + showSumOnEachDeposit());
    }
}
class MoneyStack{//когда клиент вносит деньги - вызываем класс
    public static final int COUNT = 7;//капсом пишут константы
    private static final int NOMINALS[] = {1, 5, 10, 50, 100, 500, 1000}; //номиналы
    private int amounts[] = new int[COUNT]; //количество банкнот
    MoneyStack(int[] amounts){
        this.amounts = amounts;
    }

    //когда клиент забирает  - выделяет подпачки банкнот (выделять %1000, %500, %100)
    //генерировать исключения NotEnoughBanknotesException - обрабатывать исключение в switch
    //массив из у.е.
    MoneyStack selectMoneyStackBy(int sum) throws NotEnoughBanknotesException {//выбрать стек на сумму
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
//    MoneyStack withdrawMoneyStack(int sum) throws NotEnoughBanknotesException{
//        int newAmounts[] = new int[COUNT];
//        for (int i)
//    }
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
