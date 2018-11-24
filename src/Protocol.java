import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Protocol {
    private Socket socket;
    private DataOutputStream dout;
    private DataInputStream din;

    //Client
    private Client client;
    //for client process this field isn't needed
    //for server this is the connected client
    constructions DB = new constructions();

    public Protocol(Socket socket) {
        this.socket = socket;
        try{
            this.din=new DataInputStream(socket.getInputStream());
            this.dout=new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
            //error occurred
        }
    }
    // Client Protocol
    public void startClient(){
        while(true){
            try{
                //to be changed
                dout.writeUTF("client");
                String server_string = din.readUTF().toString();
                Scanner user_input = new Scanner(System.in);
                String user_response;
                System.out.println(server_string);

                user_response = user_input.nextLine();
                dout.writeUTF(user_response);
                //TODO: logic for client
                if(user_response.equals(Constants.LOGIN)){
                   if(!login_client()) {
                       continue;
                   }
                    next_step_client();
                }
                else if(user_response.equals(Constants.SIGNUP)){
                     sign_up_client();
                     next_step_client();
                }
                else{
                    System.out.println(din.readUTF());
                    continue;
                }
                System.out.println(din.readUTF());
                break;
            }catch(IOException e){
                //handle error
            }
        }
    }

    // server protocol
    public void startServer(){

            try {
                while(true) {
                    String who = din.readUTF();
                    if(who.equals("police")){
                        //another server
                        connect_with_server();
                        break;
                    }
                    //client
                    String readString;
                    dout.writeUTF(Constants.INITIAL_STEP);
                    readString = din.readUTF().toString();
                    //TODO: logic for server
                    if (readString.equals(Constants.LOGIN)) {
                        client = login();
                        if (client.getName().equals("")) {
                            continue;
                        }
                        next_step();
                    } else if (readString.equals(Constants.SIGNUP)) {
                        client = sign_up();
                        //insert in DB
                        DB.insert_new(client);
                        next_step();
                    } else {
                        dout.writeUTF(Constants.ERROR);
                        continue;
                    }
                    dout.writeUTF(Constants.GOODBYE);
                    break;
                }
            }
            catch(IOException e){
                //handle the error
                System.out.println(e);
            }
        }



    //server functions
    public Client sign_up ()throws IOException{
        Client client = new Client();
        String client_response;
        //user name
        dout.writeUTF(Constants.ENTER_YOUR_NAME);
        client_response = din.readUTF();
        client.setName(client_response);
        //user password
        dout.writeUTF(Constants.ENTER_YOUR_PASSWORD);
        //decrypt the password
        client_response = CryptString.Decrypt(din.readUTF());
        client.setPassword(client_response);
        //user amount of money
        dout.writeUTF(Constants.ENTER_YOUR_INITIAL_BALANCE);
        client_response = din.readUTF();
        client.setAmountOfMoney(Float.parseFloat(client_response));
        // generate id (4 digits) uses charSet and numbers
        String unique_id = randomID();
        while (DB.check_if_user_is_here(unique_id)){
            unique_id = randomID();
        }
        client.setId(unique_id);
        dout.writeUTF(Constants.YOUR_UNIQUE_ID+": "+unique_id);
        //store in DB

        return client;
    }

    public String randomID(){
        String CharSet = "ABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
        String numberSet = "0123456789";
        String Token = "";
        for (int a = 1; a <= 4; a++) {
            Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
        }
        return Token;
    }

    public Client login() throws IOException{
        String account_num,password;
        dout.writeUTF(Constants.ENTER_YOUR_ACCOUNT_NUMBER);
        account_num = din.readUTF();
        dout.writeUTF(Constants.ENTER_YOUR_PASSWORD);
        //decrypt the password
        password = CryptString.Decrypt(din.readUTF());
        Client client = DB.getClient(password,account_num);
        if(client.getName().equals("")){
            //error User not found
            dout.writeUTF(Constants.USER_NOT_FOUND);
        } else {
            dout.writeUTF("found");
        }
        return client;
    }

    public void next_step() throws IOException{
        System.out.println("logged in");

        dout.writeUTF(Constants.LOGGED_IN+client.getName()+"\n"+Constants.REPEATED_STRING);
        while (true){
            String user_choice = din.readUTF();
            if(user_choice.equals(Constants.VIEW_CURRENT_BALANCE)){
                Client c=DB.getClient(client.getPassword(),client.getId());
                dout.writeUTF(Constants.CURRENT_BALANCE+" : "+c.getAmountOfmoney()+"\n"+Constants.REPEATED_STRING);
            }
            else if(user_choice.equals(Constants.DEPOSIT_MONEY)){
                dout.writeUTF(Constants.SPECIFY_AMOUNT_OF_MONEY);
                float money = get_valid_money('d');
                    Client c=DB.getClient(client.getPassword(),client.getId());
                    c.deposit(money);
                    DB.update_client(c);
                    dout.writeUTF(Constants.DONE + "\n"+ Constants.REPEATED_STRING);

            }
            else if(user_choice.equals(Constants.WITHDRAW_FROM_YOUR_BALANCE)){
                dout.writeUTF(Constants.SPECIFY_AMOUNT_OF_MONEY);
                float money=get_valid_money('w');
                Client c=DB.getClient(client.getPassword(),client.getId());
                 //valid money
                    c.withdraw(money);
                    DB.update_client(c);
                    dout.writeUTF(Constants.DONE + "\n" + Constants.REPEATED_STRING);

            }
            else if(user_choice.equals(Constants.TRANSFER_MONEY)){
                dout.writeUTF(Constants.SPECIFY_ACCOUNT_NUMBER);
                String account_number = (din.readUTF());
                dout.writeUTF(Constants.SPECIFY_AMOUNT_OF_MONEY);
                Float money = get_valid_money('t');
                //check amount of money
                client = DB.getClient(client.getPassword(),client.getId());
                //money is valid
                    //look for account num if in DB OK
                //account number length =4 hence it's in the same DB
                    Transaction transaction = new Transaction(client.getId(),account_number,money);
                    if(account_number.length()==4){
                        if(DB.transfer_money(transaction))
                        dout.writeUTF(Constants.DONE+"\n"+Constants.REPEATED_STRING);

                        else
                            dout.writeUTF(Constants.ERROR+"\n"+Constants.REPEATED_STRING);
                    }
                    //else get the suffix of the server and send to it
                    else{
                        //connect to servers as client
                        //get ip address of all servers
                        ServerObject serverObject= getServer(account_number.substring(0,4));
                        //if no server
                        if(serverObject.getPortNo()==0){
                            dout.writeUTF(Constants.ERROR+"\n"+Constants.REPEATED_STRING);
                        }
                        else{


                            Socket c = new Socket(serverObject.getIp(),serverObject.getPortNo());
                            DataInputStream din_server = new DataInputStream(c.getInputStream());
                            DataOutputStream dout_server = new DataOutputStream(c.getOutputStream());
                            dout_server.writeUTF("police");
                            dout_server.writeUTF(client.getId());
                            dout_server.writeUTF(account_number.substring(4,8));
                            String found = din_server.readUTF();
                            if(found.equals("yes")){
                                client = DB.getClient(client.getPassword(),client.getId());
                                dout_server.writeUTF(Double.toString(client.getAmountOfmoney()));
                                String from_name=getServer_fromIp(get_IP()).getName();
                                if(from_name.length()>0){
                                    dout_server.writeUTF(client.getName()+"("+from_name+")");
                                }
                                else{
                                    dout_server.writeUTF(client.getName()+"(out)");
                                }
                                dout_server.writeUTF(Float.toString(money));
                                String to_name = din_server.readUTF();
                                Double to_before = Double.parseDouble(din_server.readUTF());
                                Transaction transaction_inserted = new Transaction(client.getId(),account_number.substring(4,8),money);
                                transaction_inserted.setFrom_name(client.getName());
                                transaction_inserted.setTo_name(to_name);
                                client = DB.getClient(client.getPassword(),client.getId());
                                transaction_inserted.setAmount_from_before(client.getAmountOfmoney());
                                transaction_inserted.setAmount_to_before(to_before);
                                DB.insert_transaction(transaction_inserted);
                                client = DB.getClient(client.getPassword(),client.getId());
                                client.withdraw(money);
                                DB.update_client(client);
                                dout.writeUTF(din_server.readUTF()+"\n"+Constants.REPEATED_STRING);
                            }else{
                                dout.writeUTF(Constants.USER_NOT_FOUND+"\n"+Constants.REPEATED_STRING);
                            }


                        }

                    }


            }
            else if(user_choice.equals(Constants.VIEW_TRANSACTIONS)){
                //load all transactions from DB
                ArrayList<Transaction>transactions=DB.getTransactions(client);
                String server_response="";
                for(int i=0;i<transactions.size();i++){
                    server_response+="from : "+transactions.get(i).getFrom_id()+"("+transactions.get(i).getFrom_name()+")"+"\t\t"
                            +"to : "+transactions.get(i).getTo_id()+"("+transactions.get(i).getTo_name()+")"+"\t\t"
                            +"amount : "+transactions.get(i).getAmount()+"\n";
                }


                dout.writeUTF(server_response+"\n"+Constants.DONE+"\n"+Constants.REPEATED_STRING);
            }
            else if(user_choice.equals(Constants.LOGOUT)){
                //store client values again into DB
                //and tells client to end the session
                break;
            }
            else{
                dout.writeUTF(Constants.OPTION_NOT_DEFIEND + "\n"+ Constants.REPEATED_STRING);
            }
        }
    }

    public void connect_with_server() throws IOException{
        String from_id = din.readUTF();
        String to_id = din.readUTF();
        if(DB.check_if_user_is_here(to_id)){
            //user found
            dout.writeUTF("yes");
            Client to_client = DB.get_client(to_id);
            Double to_before = to_client.getAmountOfmoney();
            String to_name  = to_client.getName();
            Float from_before = Float.parseFloat(din.readUTF());
            String from_name = din.readUTF();
            Float money = Float.parseFloat(din.readUTF());
            String to_bank_name = getServer_fromIp(get_IP()).getName();
            if(to_bank_name.length()>0){
               to_name+="("+to_bank_name+")";
            }
            else{
                to_name+="(out)";
            }
            dout.writeUTF(to_name);
            dout.writeUTF(Double.toString(to_before));
            Transaction transaction = new Transaction(from_id,to_id,money);
            transaction.setAmount_from_before(from_before);
            transaction.setFrom_name(from_name);
            if(DB.transfer_money(transaction)){
                dout.writeUTF(Constants.DONE);
            }
            else{
                dout.writeUTF(Constants.ERROR);
            }
        }else{
            dout.writeUTF("no");
        }

    }


    //client functions
    public void sign_up_client() throws IOException{
        String server_response;
        String name,password,id;
        float amountOfMoney;
        Scanner scanner = new Scanner(System.in);
        //name
        server_response = din.readUTF().toString();
        System.out.println(server_response);
        name=scanner.nextLine();
        dout.writeUTF(name);
        //password
        server_response = din.readUTF().toString();
        System.out.println(server_response);
        password=scanner.nextLine();
        //send password encrypted
        dout.writeUTF(CryptString.Encrypt(password));
        // initial balance
        server_response = din.readUTF().toString();
        System.out.println(server_response);
        String money_string = scanner.nextLine();
        while((!(money_string.chars().allMatch(Character::isDigit)))||(Double.parseDouble(money_string)<0)){
            System.out.println(Constants.MONEY_ERROR + "\n" + Constants.MONEY_VALID);
            money_string = scanner.nextLine();
        }
        amountOfMoney=Float.parseFloat(money_string);
        dout.writeUTF(Float.toString(amountOfMoney));
        //unique id
        id = din.readUTF().toString();
        System.out.println(id);

    }

    public boolean login_client()throws IOException{
        boolean logged_in = true;
        String server_response = din.readUTF();
        Scanner scanner = new Scanner(System.in);
        System.out.println(server_response);
        String account_num,password;
        account_num = scanner.nextLine();
        dout.writeUTF(account_num);
        System.out.println(din.readUTF());
        password = scanner.nextLine();
        //send password encrypted
        dout.writeUTF(CryptString.Encrypt(password));
        server_response = din.readUTF();
        if(server_response.equals(Constants.USER_NOT_FOUND)){
            System.out.println(server_response);
            logged_in = false;
        }
        return logged_in;
    }

    public void next_step_client() throws IOException{
        //next_step for client
        Scanner scanner = new Scanner(System.in);
        while(true){
            String s;
            s=din.readUTF();
            System.out.println(s);
            String user_choice = scanner.nextLine();
            dout.writeUTF(user_choice);
            if(user_choice.equals(Constants.LOGOUT))
                break;

        }
    }

    public float get_valid_money(char op) throws IOException{
        String money_string = din.readUTF();
        float money;
        Client c=DB.getClient(client.getPassword(),client.getId());
        while((!money_string.chars().allMatch(Character::isDigit))||
                (Float.parseFloat(money_string)<=0 ||(((op=='w')||(op=='t'))&& Float.parseFloat(money_string)> c.getAmountOfmoney()))){
            dout.writeUTF(Constants.MONEY_ERROR+"\n"+Constants.MONEY_VALID);
            money_string = din.readUTF();
        }
        money = Float.parseFloat(money_string);
        return money;
    }

    public ServerObject getServer(String suffix){
        String name="",ip="";
        int portNo=0;
        try {
            String path = Protocol.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.replace("Server.jar","");
            File inputFile = new File(path+"\\config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Server");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(suffix.equals(eElement.getAttribute("suffix"))){
                        name = eElement.getAttribute("name");
                        ip=eElement.getAttribute("ip");
                        portNo=Integer.parseInt(eElement.getAttribute("portNo"));
                        break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ServerObject(name,ip,suffix,portNo);
    }
    public static ServerObject getServer_fromIp(String ip){
        String name="";
        int portNo=0;
        String suffix="";
        try {
            String path = Protocol.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.replace("Server.jar","");
            File inputFile = new File(path+"\\config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Server");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(ip.equals(eElement.getAttribute("ip"))){
                        name = eElement.getAttribute("name");
                        suffix=eElement.getAttribute("suffix");
                        portNo=Integer.parseInt(eElement.getAttribute("portNo"));
                        break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ServerObject(name,ip,suffix,portNo);
    }
    //get IP
    public static String get_IP(){
        String ip="";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
             ip = socket.getLocalAddress().getHostAddress();
        }
        catch (Exception e){

        }
        return ip;
    }

    //for client process to get ip and portNo to connect to
    public static ServerObject  getServer_data(){

        String ip="";
        int portNo=0;
        try {
            String path = Protocol.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.replace("Client.jar","");
            File inputFile = new File(path+"\\config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Client");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                        ip=eElement.getAttribute("ip");
                        portNo=Integer.parseInt(eElement.getAttribute("portNo"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ServerObject("",ip,"",portNo);
    }



    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    public void setDout(DataOutputStream dout) {
        this.dout = dout;
    }

    public DataInputStream getDin() {
        return din;
    }

    public void setDin(DataInputStream din) {
        this.din = din;
    }
}
