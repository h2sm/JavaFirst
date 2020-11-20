import java.util.*;

public class third {
    public static void main(String[] args) {
        BankManagement open = new BankManagement();
        open.loadClients();
        open.test();
    }
}
class BankManagement{
    Bank mainBank = new Bank();
    void loadClients(){//загружаем изначальный массив клиентов
        mainBank.addClient("Евгений Бланов");
        mainBank.addClient("Сосиска Наталья Витальевна");
        mainBank.addClient("Борис Олегович Ганалов");
        mainBank.addClient("Шмаль Анна Николаевна");
        mainBank.addClient("Уланов Евгений Бок");
        mainBank.addClient("Магомедов Зорро");
        mainBank.addClient("Россия Беспутина");
        mainBank.addClient("Казимир Кукуруза");
        mainBank.addClient("Василий ");
//        mainBank.showMoneyOnAllDeps(0 );
//        mainBank.addMoneyToDeposit(0, 0, 100);
//        mainBank.withdrawMoneyFromDeposit(0,2,200);
        //mainBank.deleteClient(0);
        //mainBank.deleteOneDeposit(0, 1);
    }
    void test(){
        Random x = new Random();
        for (int i=0; i<100;i++){
            //запроить список клиентов -> генерировать рандомный элемент с ограничением
            switch (x.nextInt(8)){ //новый свитч сделай
                //вместо sum передавать массив с банкнотами(генерировать случайный moneystack)

                case 0: mainBank.addMoneyToDeposit(0,0,400);//выводим сообщения здесь!!!!!!
                    break;
                case 1:
                    mainBank.addMoneyToDeposit(0,1,300);
                    break;
                case 2:
                    mainBank.addMoneyToDeposit(0,1,350);
                    break;
                case 3:
                    mainBank.addMoneyToDeposit(0,2,400);
                    break;
                case 4:
                    mainBank.deleteClient(0);
                    break;
                case 5:
                    mainBank.deleteOneDeposit(0,2);
                    break;
                case 6:
                    mainBank.openNewDeposit(0);
                    break;
                case 7:
                    mainBank.addMonthlyRate(0);
                    break;
            }
        }
    }
}
class Bank{
    private ArrayList<String> listOfClients = new ArrayList<>();//список клиентов
    //private ArrayList<Client> clients = new ArrayList<>();//массив клиентов и их депозитов
    final MoneyStack bankCapit = new MoneyStack(new int[] {100,100,100,100,100,100,100});
    Map<String, Client> map = new Map<String, Client>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Client get(Object key) {
            return null;
        }

        @Override
        public Client put(String key, Client value) {
            return null;
        }

        @Override
        public Client remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends Client> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return null;
        }

        @Override
        public Collection<Client> values() {
            return null;
        }

        @Override
        public Set<Entry<String, Client>> entrySet() {
            return null;
        }
    };

    public ArrayList<String> getListOfClients() {
        return listOfClients;
    }

    public void setListOfClients(ArrayList<String> listOfClients) {
        this.listOfClients = listOfClients;
    }

    public MoneyStack getBankCapit() {
        return bankCapit;
    }

    void addClient(String nameOfClient){//добавление клиента
        if(listOfClients.contains(nameOfClient)){//проверка на повторное добавление
            System.out.println("Клиент " + nameOfClient+ " уже есть");
        }
        else{
            listOfClients.add(nameOfClient);//добавляем клиента если его не было
            map.put(nameOfClient, new Client(nameOfClient));
            System.out.println(nameOfClient + " добавлен");
        }
    }
    void deleteClient(Integer ID){//процедура удаления клиента + ПЕРЕДАВАЙ Client
        //showMoneyOnAllDeps(ID); //для быстрой проверки
        String name = listOfClients.get(ID);//имя клиента
        Client x = clients.get(listOfClients.indexOf(name));//достаем клиента
        x.deleteAllDeposits();//удаляем все его депозиты
        clients.remove(ID);//удаляем его инстанс
        listOfClients.remove(ID);//удаляем клиента из списков клиента
        System.out.println(name + " удален");//здоровья погибшим
        //showMoneyOnAllDeps(ID); // для проверки
    }
    void deleteOneDeposit(int ID, int numOfDep){//процедура удаления определенного депозита
        Client x = clients.get(ID);
        x.deleteDeposit(numOfDep);
        System.out.println("Клиент " + listOfClients.get(ID) + " удалил депозит " + numOfDep +  ". Остаток: " + x.showSumOnEachDeposit());
    }
    void openNewDeposit(int ID){//открытие депозита
        Client x = clients.get(ID);
        x.addDeposit();
        System.out.println("Клиент " + listOfClients.get(ID) + " открыл депозит "  +  ". Остаток: " + x.showSumOnEachDeposit());

    }
    void showMoneyOnAllDeps(int ID){//показать сумму всех депозитов
        Client x = clients.get(ID);
        System.out.println("У клиента " +listOfClients.get(ID) +" сумма на депозитах "+ x.showSumOnEachDeposit());
    }
    void addMoneyToDeposit(int ID, int numOfDep, int sum){//добавить денег на определенный депозит
        Client x = clients.get(ID);
        x.addSumOnDeposit(sum,numOfDep);
        System.out.println("Клиент " + listOfClients.get(ID) + " добавил денег " + sum + " на депозит " + numOfDep + ". Остаток:" + x.showSumOnEachDeposit());
    }
    void withdrawMoneyFromDeposit(int ID, int numOfDep, int sum){//снять денег с определенного депозита
        Client x = clients.get(ID);
        x.withdrawMoneyFromDeposit(sum,numOfDep);
        System.out.println("Клиент " + listOfClients.get(ID) + " снял " + sum + " с депозита " + numOfDep + ". Остаток:" + x.showSumOnEachDeposit());
    }
    void addMonthlyRate(int ID){
        Client x = clients.get(ID);
        x.monthRate();
    }
}
class Client{
    private final ArrayList<Integer> deposits = new ArrayList<>();//все поля защищенные + сеттер и геттер (дописать)
    private final String clientName;
    private final int rate = 5; //ставка - 5 процентных пунктов
    Client(String name){
        this.clientName = name;
    }
    void addSumOnDeposit(Integer sum, Integer numOfDeposit) {//добавить сумму на определенный депозит
        if (deposits.size() < numOfDeposit){//если зачисление на счет, который еще не открыли
            System.out.println("Зачисление невозможно, депозит " + numOfDeposit + " еще не открыт" );
        }
        else if (deposits.isEmpty()) {
            capitalAdd(sum);//добавляем средства банку
            deposits.add(sum);//добавляем денег клиенту
            }
        else{
            deposits.set(numOfDeposit, sum + showSumOnOneDeposit(numOfDeposit));
                //bankCapit+=sum;
                capitalAdd(sum);
            }
        }
    Integer getCountOfDeposits(){//количество депозитов клиента
        return deposits.size();
    }
    ArrayList showSumOnEachDeposit(){//сумма на всех депозитах клиента
        //System.out.println(deposits);
        return deposits;
    }
    Integer showSumOnOneDeposit(Integer numOfDeposit){//сумма денег на определенном депозите
        if (deposits.size() <= numOfDeposit) {
            return 0;
        }
        else {
            return deposits.get(numOfDeposit);
        }
    }
    void withdrawMoneyFromDeposit(int sum, int numOfDeposit) {//снять сумму с определенного депозита
        if ((showSumOnOneDeposit(numOfDeposit) - sum ) > 0 && capitalWithdraw(sum)) {//если денег для снятия достаточно как у банка, так и у клиента
            deposits.set(numOfDeposit, showSumOnOneDeposit(numOfDeposit) - sum);

        }
        else{//если недостаточно
            System.out.println("Недостаточно средств для списания");//взаимодействие консоли только через main, не в методах
        }
    }
    void addDeposit(){//открытие нового депозита
        if(deposits.size() <5){
            deposits.add(0);
        }
        else{
            System.out.println("Более 5 депозитов. Открытие нового невозможно");
        }
    }
    void deleteDeposit(Integer numOfDeposit){//удаляем отдельно взятый депозит
        deposits.remove(showSumOnOneDeposit(numOfDeposit));
    }
    void deleteAllDeposits(){//удаляем все депозиты
        deposits.clear();//удаляй его вася
    }
    boolean capitalWithdraw(Integer sum) {//вычитание денег из капитала при снятии средств клиентом. возвращает тру, если капитал достаточен
        if ((bankCapit - sum) < 0 ){//проверка, есть ли у банка средства
            System.out.println("Дорогие друзья..... Всё! Денег нет, но вы держитесь");
            System.exit(0);//банкротство. рип вкладчик 2020-2020
        }
        else{
            bankCapit-=sum;//автоматическое списание денег из капитала
        }
        return true;
    }
    void capitalAdd(Integer sum){//добавление денег в капитал банка
        bankCapit+=sum;
    }
    void monthRate(){
        for (int i = 0; i< deposits.size(); i++){
            int x = deposits.get(i);
            int y = x*rate/100;
            x+=y;
            deposits.set(i,x);
        }
        System.out.println("да");
    }


}
class MoneyStack{//когда клиент вносит деньги - вызываем класс
    public static final int COUNT = 7;//капсом пишут константы
    private static final int NOMINALS[] = {1, 5, 10, 50, 100, 500, 1000};//номиналы
    private int amounts[] = new int[COUNT];//количество банкнот
    MoneyStack(int[] amounts){
        this.amounts = amounts;
    }

    //когда клиент забирает  - выделяет подпачки банкнот (выделять %1000, %500, %100)
    //генерировать исключения NotEnoughBanknotsException - обрабатывать исключение в switch
    //массив из у.е.
    MoneyStack selectMoneyStackBy(int sum) throws NotEnoughBanknotsException {//выбрать стек на сумму
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
           throw new NotEnoughBanknotsException("Денег нет, но вы держитесь");
       }
    }
}
class BankException extends Exception{
    //исключения наследуются через этот класс
    BankException(String msg){
        super(msg);
    }
    BankException(){}
}
class NotEnoughBanknotsException extends BankException{
    NotEnoughBanknotsException(String msg){
        super(msg);
    }
}
