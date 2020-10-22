import java.util.*;

public class third {
    public static void main(String[] args) {
        BankManagement open = new BankManagement();
        open.loadClients();
    }
}
class BankManagement{
    Bank mainBank = new Bank();
    void loadClients(){//загружаем изначальный массив клиентов
        mainBank.addClient("Евгений Бланов (Е.Бланов)");
        mainBank.addClient("Сосиска Наталья Витальевна");
        mainBank.addClient("Вагина Ирина");
        mainBank.addClient("Шмаль Анна Николаевна");
        mainBank.addClient("Уланов Евгений Бок (У.Е.Бок)");
        mainBank.addClient("Магомедов Зорро");
        mainBank.addClient("Россия Беспутина");
        mainBank.addClient("Казимир Кукуруза");
        mainBank.addClient("Василий Ебание");
        mainBank.addMoneyToDeposit(0,0,400);
        mainBank.addMoneyToDeposit(0,1,300);
        mainBank.addMoneyToDeposit(0,1,350);
        mainBank.addMoneyToDeposit(0,2,400);
        mainBank.showMoneyOnAllDeps(0 );
        mainBank.withdrawMoneyFromDeposit(0,2,200);
        mainBank.addMoneyToDeposit(0, 0, 100);
        mainBank.deleteClient(0);
        //mainBank.deleteOneDeposit(0, 1);
    }
    void operations(){
        for (int i =0; i<8644;i++){//каждая итерация - новый час
            int x = (int) (Math.random()) *10;
            switch (x) {
                case '1':
            }
        }

    }
}
class Bank{
    ArrayList<String> listOfClients = new ArrayList<>();//список клиентов
    ArrayList<Client> clients = new ArrayList<>();//массив клиентов и их депозитов
    void addClient(String nameOfClient){//добавление клиента
        if(listOfClients.contains(nameOfClient)){//проверка на повторное добавление
            System.out.println("Клиент " + nameOfClient+ " уже есть");
        }
        else{
            listOfClients.add(nameOfClient);//добавляем клиента если его не было
            clients.add(new Client(nameOfClient));//создаем инстанс клииента с его депозитами
            System.out.println(nameOfClient + " добавлен");
        }
    }
    void deleteClient(Integer ID){//процедура удаления клиента
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
    //разместить массив клиентов
    //добавление + удаление
}
class Client{
    ArrayList<Integer> deposits = new ArrayList<>();
    String clientName;
    double bankCapit = 100000.0;//запас банкнот банка
    Client(String name){
        this.clientName = name;
    }
    void addSumOnDeposit(Integer sum, Integer numOfDeposit) throws IndexOutOfBoundsException{//добавить сумму на определенный депозит
        try {
            if (deposits.isEmpty()) {// НЕ ЛЕЗЬ РАБОТАЕТ !!!!!!!!!
                capitalAdd(sum);
                deposits.add(numOfDeposit, sum);
                //bankCapit+=sum;
            }
            else{
                deposits.set(numOfDeposit, sum + showSumOnOneDeposit(numOfDeposit));
                //bankCapit+=sum;
                capitalAdd(sum);
            }
        }
        catch (Exception exception){
            deposits.add(numOfDeposit,sum);
            bankCapit+=sum;
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
        return deposits.get(numOfDeposit);
    }
    void withdrawMoneyFromDeposit(Integer sum, Integer numOfDeposit){//снять сумму с определенного депозита
        if ((showSumOnOneDeposit(numOfDeposit) - sum ) > 0 && capitalWithdraw(sum)) {//если денег для снятия достаточно как у банка, так и у клиента
            deposits.set(numOfDeposit, showSumOnOneDeposit(numOfDeposit) - sum);
        }
        else{//если недостаточно
            System.out.println("Недостаточно средств для списания");
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

}
