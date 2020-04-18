package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.MatrixUtils;
import ru.geekbrains.stargame.math.Rect;

public abstract class BaseScreen implements Screen, InputProcessor {

  protected final Music music;

  protected SpriteBatch batch;
  protected Vector2     touch;
  protected Rect        worldBounds;

  private Matrix4 worldToGl;
  private Matrix3 screenToWorld;
  private Rect    screenBounds;
  private Rect    glBounds;

  public BaseScreen() {
    music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
    music.setLooping(true);
    music.play();
  }

  @Override
  public void show() {
    System.out.println("show");
    batch = new SpriteBatch();
    Gdx.input.setInputProcessor(this);
    screenBounds  = new Rect();
    worldBounds   = new Rect();
    glBounds      = new Rect(0, 0, 1f, 1f);
    worldToGl     = new Matrix4();
    screenToWorld = new Matrix3();
    touch         = new Vector2();
  }

  @Override
  public void render(float delta) {}

  @Override
  public void resize(int width,
                     int height) {
    System.out.println("resize width = " + width + " height = " + height);
    screenBounds.setSize(width, height);
    screenBounds.setLeft(0);
    screenBounds.setBottom(0);

    float aspect = width / (float) height;
    worldBounds.setHeight(1f);
    worldBounds.setWidth(1f * aspect);
    MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
    batch.setProjectionMatrix(worldToGl);
    MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
    resize(worldBounds);
  }

  public void resize(Rect worldBounds) {
    System.out.println(
        "worldBounds width = " + worldBounds.getWidth() + " height = " + worldBounds.getHeight());
  }

  @Override
  public void pause() {
    System.out.println("pause");
    music.pause();
  }

  @Override
  public void resume() {
    System.out.println("resume");
    music.play();
  }

  @Override
  public void hide() {
    System.out.println("hide");
    dispose();
  }

  @Override
  public void dispose() {
    System.out.println("dispose");
    music.dispose();
  }

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
  public boolean touchDown(int screenX,
                           int screenY,
                           int pointer,
                           int button) {
    System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
    calcTouch(screenX, screenY);
    touchDown(touch, pointer, button);
    return false;
  }

  @Override
  public boolean touchUp(int screenX,
                         int screenY,
                         int pointer,
                         int button) {
    System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
    calcTouch(screenX, screenY);
    touchUp(touch, pointer, button);
    return false;
  }

  public boolean touchUp(Vector2 touch,
                         int pointer,
                         int button) {
    System.out.println(
        "touchUp Vector touch X = "
        + touch.x
        + " touch Y = "
        + touch.y
        + " pointer = "
        + pointer
        + " button = "
        + button);
    return false;
  }

  @Override
  public boolean touchDragged(int screenX,
                              int screenY,
                              int pointer) {
    System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
    calcTouch(screenX, screenY);
    touchDragged(touch, pointer);
    return false;
  }

  public boolean touchDragged(Vector2 touch,
                              int pointer) {
    System.out.println(
        "touchDragged touch X = " + touch.x + " touch Y = " + touch.y + " pointer " + pointer);
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX,
                            int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    System.out.println("a scrolled amount = " + amount);
    return false;
  }

  protected void calcTouch(int screenX,
                           int screenY) {
    touch.set(screenX, screenBounds.getHeight() - screenY)
         .mul(screenToWorld);
  }

  public boolean touchDown(Vector2 touch,
                           int pointer,
                           int button) {
    System.out.println(
        "touchDown Vector touch X = "
        + touch.x
        + " touch Y = "
        + touch.y
        + " pointer "
        + pointer
        + " button "
        + button);
    return false;
  }
}
