public class Entry {
    public static void main(String[] args){

    }
}
/*
*
*
* */
class Queue{
    private int capacity;
    private int head=0;
    private int tail=0;
    String[] queue;
    public Queue(int cap){
        this.capacity = cap;
        queue = new String[cap];
    }
    public boolean inQueue(String x){
        if((tail + 1)%capacity == head){
            return false;
        }else{
            queue[tail] = x;
            tail = (tail + 1) % capacity;
            return true;
        }
    }
    public String deQueue(){
        if(head == tail){
            return null;
        }
        String tmp = queue[head];
        queue[head] = null;
        head = (head + 1) % capacity;
        return tmp;
    }
}
