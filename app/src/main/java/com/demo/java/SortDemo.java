package com.demo.java;

/**
 * Created by ludexiang on 2018/3/5.
 */

class InsertSort {

  /**
   * 插入排序
   */
  public void insertSort(int[] a) {
    for (int i = 1; i < a.length; i++) {
      int temp = a[i];
      int j = i - 1;
      for (; j >= 0 && temp < a[j]; j--) {
        a[j + 1] = a[j];
      }
      a[j + 1] = temp;
    }
  }
}

/**
 * 冒泡
 */
class BubbleSort {

  public void bubbleSort(int[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      for (int j = 0; j < a.length - 1 - i; j++) {
        if (a[j] > a[j + 1]) {
          int temp = a[j];
          a[j] = a[j + 1];
          a[j + 1] = temp;
        }
      }
    }
  }
}

/**
 * 选择
 */
class SelectSort {

  public void selectSort(int[] a) {
    for (int i = 0; i < a.length - 1; i++) {
      for (int j = i + 1; j < a.length; j++) {
        if (a[i] > a[j]) {
          int temp = a[i];
          a[i] = a[j];
          a[j] = temp;
        }
      }
    }
  }
}

/**
 * 快速
 */
class QuickSort {

  public void quickSort(int array[], int low, int high) {// 传入low=0，high=array.length-1;
    int pivot, p_pos, i, t;// pivot->位索引;p_pos->轴值。
    if (low < high) {
      p_pos = low;
      pivot = array[p_pos];
      for (i = low + 1; i <= high; i++) {
        if (array[i] < pivot) {
          p_pos++;
          t = array[p_pos];
          array[p_pos] = array[i];
          array[i] = t;
        }
      }
      t = array[low];
      array[low] = array[p_pos];
      array[p_pos] = t;
      // 分而治之
      quickSort(array, low, p_pos - 1);// 排序左半部分
      quickSort(array, p_pos + 1, high);// 排序右半部分
    }
  }
}

public class SortDemo {

  public static void main(String[] args) {
    // insert sort
    int[] insertArray = {5, 9, 2, 1, 0, 8, 7, 4, 3, 6};
    InsertSort insert = new InsertSort();
    insert.insertSort(insertArray);
    for (int i = 0; i < insertArray.length; i++) {
      System.out.print(insertArray[i] + " ");
    }
    System.out.println();

    // 冒泡
    int[] bubbleArray = {5, 9, 2, 1, 0, 8, 7, 4, 3, 6};
    BubbleSort bubble = new BubbleSort();
    bubble.bubbleSort(bubbleArray);
    for (int i = 0; i < bubbleArray.length; i++) {
      System.out.print(bubbleArray[i] + " ");
    }
    System.out.println();

    // 选择
    int[] selectArray = {5, 9, 2, 1, 0, 8, 7, 4, 3, 6};
    SelectSort selectSort = new SelectSort();
    selectSort.selectSort(selectArray);
    for (int i = 0; i < selectArray.length; i++) {
      System.out.print(selectArray[i] + " ");
    }
    System.out.println();

    // 快速
    int[] quickArray = {5, 9, 2, 1, 0, 8, 7, 4, 3, 6};
    QuickSort quickSort = new QuickSort();
    quickSort.quickSort(quickArray, 0, quickArray.length - 1);
    for (int i = 0; i < quickArray.length; i++) {
      System.out.print(quickArray[i] + " ");
    }
    System.out.println();
  }


}
