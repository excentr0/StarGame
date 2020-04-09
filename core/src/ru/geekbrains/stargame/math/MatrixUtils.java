package ru.geekbrains.stargame.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Утилита для работы с матрицами
 */
public class MatrixUtils {

  private MatrixUtils() {}

  /**
   * Расчёт матрицы перехода 4x4
   *
   * @param mat итоговая матрица преобразований
   * @param src исходный квадрат
   * @param dst итоговый квадрат
   */
  public static void calcTransitionMatrix(Matrix4 mat,
                                          Rect src,
                                          Rect dst) {
    float scaleX = dst.getWidth() / src.getWidth();
    float scaleY = dst.getHeight() / src.getHeight();
    mat.idt()
       .translate(dst.position.x, dst.position.y, 0f)
       .scale(scaleX, scaleY, 1f)
       .translate(-src.position.x, -src.position.y, 0f);
  }

  /**
   * Расчёт матрицы перехода 3x3
   *
   * @param mat итоговая матрица преобразований
   * @param src исходный квадрат
   * @param dst итоговый квадрат
   */
  public static void calcTransitionMatrix(Matrix3 mat,
                                          Rect src,
                                          Rect dst) {
    float scaleX = dst.getWidth() / src.getWidth();
    float scaleY = dst.getHeight() / src.getHeight();
    mat.idt()
       .translate(dst.position.x, dst.position.y)
       .scale(scaleX, scaleY)
       .translate(-src.position.x, -src.position.y);
  }
}
