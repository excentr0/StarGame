package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseScreen implements Screen, InputProcessor {

  protected SpriteBatch batch;

  @Override
  public boolean keyDown(int keycode) {
    System.out.println("keyDown keycode = " + keycode);
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    System.out.println("keyUp keycode = " + keycode);
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    System.out.println("keyTyped character = " + character);
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    System.out.printf(
        "touchDown screenX = %d, screenY = %d, pointer = %d, button = %d%n",
        screenX, screenY, pointer, button);
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    System.out.printf(
        "touchUp screenX = %d, screenY = %d, pointer = %d, button = %d%n",
        screenX, screenY, pointer, button);
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    System.out.printf(
        "touchDragged screenX = %d, screenY = %d, pointer = %d%n", screenX, screenY, pointer);
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    System.out.println("scrolled amount = " + amount);
    return false;
  }

  @Override
  public void show() {
    System.out.println("show");
    batch = new SpriteBatch();
    Gdx.input.setInputProcessor(this);
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
