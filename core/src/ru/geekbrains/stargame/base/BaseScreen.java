package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
  @Override
  public void show() {
    System.out.println("show");
  }

  @Override
  public void render(float delta) {}

  @Override
  public void resize(int width, int height) {
    System.out.printf("resize width = %s, height = %s%n", width, height);
  }

  @Override
  public void pause() {
    System.out.println("pause");
  }

  @Override
  public void resume() {
    System.out.println("resume");
  }

  @Override
  public void hide() {
    System.out.println("hide");
    dispose();
  }

  @Override
  public void dispose() {
    System.out.println("dispose");
  }
}
