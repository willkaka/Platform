package com.platform.test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Jiqing on 2016/12/9.
 */
public class testTree2 {
    // ��������
    JFrame jf = new JFrame("����");
    JTree tree;
    DefaultMutableTreeNode root;
    DefaultMutableTreeNode guangdong;
    DefaultMutableTreeNode guangxi;
    DefaultMutableTreeNode foshan;
    DefaultMutableTreeNode shantou;
    DefaultMutableTreeNode guilin;
    DefaultMutableTreeNode nanning;
    // ��ʼ��
    public void init() {
        // �����ڵ�
        root = new DefaultMutableTreeNode("�й�");
        guangdong = new DefaultMutableTreeNode("�㶫");
        guangxi = new DefaultMutableTreeNode("����");
        foshan = new DefaultMutableTreeNode("��ɽ");
        shantou = new DefaultMutableTreeNode("��ͷ");
        guilin = new DefaultMutableTreeNode("����");
        nanning = new DefaultMutableTreeNode("����");
        // ͨ��add()�����������ڵ�֮��ĸ��ӹ�ϵ
        guangdong.add(foshan);
        guangdong.add(shantou);
        guangxi.add(guilin);
        guangxi.add(nanning);
        root.add(guangdong);
        root.add(guangxi);
        // �Ը��ڵ㴴����
        tree = new JTree(root);
        jf.add(new JScrollPane(tree));
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ùر�,�᷵��Process finished with exit code 0
        jf.setVisible(true);
    }

	public static void main(String[] args) {
        new testTree2().init();
    }

}
