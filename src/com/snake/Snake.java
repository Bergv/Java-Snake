package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
    Node head, mid, tail;
    Dir dir = Dir.L;

    Snake() {
        head = new Node(20, 20);
        mid = new Node(20, 21);
        tail = new Node(20, 22);
        head.next = mid;
        mid.prev=head;
        mid.next=tail;
        tail.prev=mid;
    }

    public void addToHead() {
        Node n = null;
        switch (dir) {
            case L:
                n = new Node(head.row, head.col - 1);
                break;
            case U:
                n = new Node(head.row - 1, head.col);
                break;
            case R:
                n = new Node(head.row, head.col + 1);
                break;
            case D:
                n = new Node(head.row + 1, head.col);
                break;
        }
        n.next = head;
        head.prev = n;
        head = n;
    }

    public void paint(Graphics g) {
        Node h = head;
        h.paintHead(g);
        Node n = head.next;
        while (n != null) {
            n.paint(g);
            n = n.next;
        }

        move();
    }

    private void move() {
        addToHead();
        deleteTail();
        boundaryCheck();
    }

    private void boundaryCheck() {
        if (head.row < 0) head.row = Yard.NodeCount - 1;
        if (head.col < 0) head.col = Yard.NodeCount - 1;
        if (head.row > Yard.NodeCount - 1) head.row = 0;
        if (head.col > Yard.NodeCount - 1) head.col = 0;
    }

    private void deleteTail() {
        if (tail == null) return;
        tail = tail.prev;
        tail.next.prev = null;
        tail.next = null;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (this.dir != Dir.R) dir = Dir.L;
                break;
            case KeyEvent.VK_UP:
                if (this.dir != Dir.D) dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                if (this.dir != Dir.L) dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:
                if (this.dir != Dir.U) dir = Dir.D;
                break;
        }
    }

    public void eat(Egg e) {
        if (head.row == e.row && head.col == e.col) {
            addToHead();
            e.reAppear();
        }
    }
}
