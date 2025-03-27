package com.example.project2;

public class DLinkedList<T extends Comparable<T>> {

    private DNode<T> head;

    public DLinkedList() {
        DNode<T> dummy = new DNode<>(null);
        head = dummy;
        head.setNext(head);
        head.setPrev(head);
    }

    public DNode<T> getHead() {
        return head;
    }

    public void insetSorted(T data) {
        DNode<T> newNode = new DNode<>(data);
        if (head.getNext() == head) {
            newNode.setNext(head);
            newNode.setPrev(head);
            head.setNext(newNode);
            head.setPrev(newNode);
            return;
        }
        DNode<T> curr = head.getNext();
        while (curr != head) {
            if (curr.compareTo(newNode) > 0)
                break;
            curr = curr.getNext();
        }
        if (curr == head) {
            newNode.setNext(head);
            newNode.setPrev(head.getPrev());
            head.getPrev().setNext(newNode);
            head.setPrev(newNode);
            return;
        }
        newNode.setPrev(curr.getPrev());
        newNode.setNext(curr);
        curr.getPrev().setNext(newNode);
        curr.setPrev(newNode);

    }

    public void traverse() {
        System.out.println(toString());
    }

    //This implementation is only for sorted list
    public void removeDuplicates() {
        DNode<T> curr = head.getNext();
        while (curr.getNext() != head){
            if (curr.getNext().getData().compareTo(curr.getData())==0) {
                curr.getNext().getNext().setPrev(curr);
                curr.setNext(curr.getNext().getNext());
                continue;
            }
            curr = curr.getNext();
        }
    }

    public void clear() {
        head.setNext(head);
        head.setPrev(head);
    }

    @Override
    public String toString() {

        String s = "";
        DNode<T> node = head.getNext();
        while (true) {
            if (node == head)
                break;
            s += node.toString();
            node = node.getNext();

        }
        return "Head->" + s + "Head";
    }


}
