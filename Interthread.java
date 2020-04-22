//Producer
class Producer implements Runnable
{
    Stock s;
    Thread t;
    Producer(Stock s){
        this.s=s;
        t = new Thread(this);
        t.start();
    }
    public void run(){
        while(true){
            try{
                Thread.sleep(80);
            }catch (InterruptedException ie){}
            s.addStock((int)(Math.random()*10));
        } //while end
    }// run end
    void end(){ t.stop(); }

} // producer class end

//Consumer
class Consumer implements Runnable
{
    Stock s;
    Thread t;
    Consumer(Stock s){
        this.s=s;
        t = new Thread(this);
        t.start();
    }
    public void run(){
        while(true){
            try{
                Thread.sleep(80);
            }catch (InterruptedException ie){}

            s.getStock((int)(Math.random()*10));
        } //while end
    }// run end
    void end(){ t.stop(); }

}// consumer class end

//Stock
class Stock
{
    int goods=0;

    public synchronized void addStock(int i){
        goods = goods + i;
        System.out.println("Stock added " + i);
        System.out.println("Present Stock is "+ goods);
        notify();
    }//end add stokc

    public synchronized int getStock(int n){
        while(true){
            if(goods >= n) {
                goods = goods - n;
                System.out.println("Stock get away is " + n);
                System.out.println("Present Stock is " + goods);
                break;
            }else {
                System.out.println("Stock is not enough ");
                try{
                    wait();
                }catch (InterruptedException ie){}
            }
        }
        return goods;
    }// end get stock


    public static void main(String args[])
    {
        Stock s = new Stock();
        Producer p = new Producer(s);
        Consumer c = new Consumer(s);
        try {
            Thread.sleep(100);
            p.end();
            c.end();
            p.t.join();
            c.t.join();
            System.out.println("Thread Stopped ");
        }catch (InterruptedException ie){}
        System.exit(0);
    }
}//end stack class
