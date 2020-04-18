package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprites.*;
import ru.geekbrains.stargame.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {
  private static final int BIG_ASTEROID_COUNT   = 5;
  private static final int MED_ASTEROID_COUNT   = 7;
  private static final int SMALL_ASTEROID_COUNT = 10;

  private final Game game;

  private TextureAtlas           gameAtlas;
  private TextureAtlas           extraAtlas; // атлас по взрывами и gameover
  private EnemyPool              enemyPool;
  private BulletPool             bulletPool;
  private ExplosionPool          explosionPool;
  private MainShip               mainShip;
  private GameOver               gameOver;
  private NewGame                newGame;
  private EnemyEmitter           enemyEmitter;
  private BackgroundSprite       backgroundSprite;
  private BigAsteroidSprite[]    bigAsteroids;
  private MediumAsteroidSprite[] mediumAsteroids;
  private SmallAsteroidSprite[]  smallAsteroids;
  private Sound                  laserSound;
  private Sound                  bulletSound;
  private Sound                  explosionSound;
  private State                  state;

  public GameScreen(final Game game) {
    this.game = game;
  }

  @Override
  public void show() {
    super.show();
    gameAtlas  = new TextureAtlas(Gdx.files.internal("textures/StarGame.atlas"));
    extraAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));

    laserSound     = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
    bulletSound    = Gdx.audio.newSound(Gdx.files.internal("sound/bullet.wav"));
    explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));

    bulletPool    = new BulletPool();
    explosionPool = new ExplosionPool(extraAtlas, explosionSound);
    enemyPool     = new EnemyPool(bulletPool, explosionPool, worldBounds);
    enemyEmitter  = new EnemyEmitter(gameAtlas, enemyPool, worldBounds, bulletSound);

    initSprites();
    state = State.PLAYING;
  }

  private void initSprites() {
    try {
      mainShip         = new MainShip(gameAtlas, bulletPool, explosionPool, laserSound);
      backgroundSprite = new BackgroundSprite(gameAtlas);
      gameOver         = new GameOver(extraAtlas);
      newGame          = new NewGame(extraAtlas, game);

      bigAsteroids    = new BigAsteroidSprite[BIG_ASTEROID_COUNT];
      mediumAsteroids = new MediumAsteroidSprite[MED_ASTEROID_COUNT];
      smallAsteroids  = new SmallAsteroidSprite[SMALL_ASTEROID_COUNT];


      for (int i = 0; i < BIG_ASTEROID_COUNT; i++) { bigAsteroids[i] = new BigAsteroidSprite(gameAtlas); }
      for (int i = 0; i < MED_ASTEROID_COUNT; i++) { mediumAsteroids[i] = new MediumAsteroidSprite(gameAtlas); }
      for (int i = 0; i < SMALL_ASTEROID_COUNT; i++) { smallAsteroids[i] = new SmallAsteroidSprite(gameAtlas); }

    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    update(delta);
    checkCollisions();
    freeAllDestroyed();
    draw();
  }

  private void update(final float delta) {
    for (final BigAsteroidSprite asteroidSprite : bigAsteroids) asteroidSprite.update(delta);
    for (final MediumAsteroidSprite mediumAsteroidSprite : mediumAsteroids) mediumAsteroidSprite.update(delta);
    for (final SmallAsteroidSprite smallAsteroidSprite : smallAsteroids) smallAsteroidSprite.update(delta);
    explosionPool.updateActiveSprites(delta);
    if (state == State.PLAYING) {
      mainShip.update(delta);
      bulletPool.updateActiveSprites(delta);
      enemyPool.updateActiveSprites(delta);
      enemyEmitter.generate(delta);
    }
  }

  private void checkCollisions() {
    if (state != State.PLAYING) {
      return;
    }
    List<Enemy> enemyList = enemyPool.getActiveObjects();
    List<Bullet> bulletList = bulletPool.getActiveObjects();
    for (Enemy enemy : enemyList) {
      if (enemy.isDestroyed()) {
        continue;
      }
      float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
      if (mainShip.position.dst(enemy.position) < minDist) {
        enemy.destroy();
        mainShip.damage(enemy.getDamage());
      }
      checkEnemyShipHit(bulletList, enemy);
    }
    checkMainShipHit(bulletList);
    if (mainShip.isDestroyed()) {
      state = State.GAME_OVER;
    }
  }

  public void freeAllDestroyed() {
    bulletPool.freeAllDestroyedActiveObjects();
    enemyPool.freeAllDestroyedActiveObjects();
    explosionPool.freeAllDestroyedActiveObjects();
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    for (final SmallAsteroidSprite smallAsteroidSprite : smallAsteroids) { smallAsteroidSprite.draw(batch); }
    for (final MediumAsteroidSprite mediumAsteroidSprite : mediumAsteroids) { mediumAsteroidSprite.draw(batch); }
    for (final BigAsteroidSprite asteroidSprite : bigAsteroids) asteroidSprite.draw(batch);

    switch (state) {
      case PLAYING:
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        mainShip.draw(batch);
        break;
      case PAUSE:
        break;
      case GAME_OVER:
        gameOver.draw(batch);
        newGame.draw(batch);
        break;
    }
    explosionPool.drawActiveSprites(batch);
    batch.end();
  }

  /**
   * Проверяем, попали ли во вражеский корабль
   *
   * @param bulletList список пуль
   * @param enemy      вражеский корабль
   */
  private void checkEnemyShipHit(final List<Bullet> bulletList,
                                 final Enemy enemy) {
    for (Bullet bullet : bulletList) {
      if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
        continue;
      }
      if (enemy.isBulletCollision(bullet)) {
        enemy.damage(bullet.getDamage());
        bullet.destroy();
      }
    }
  }

  /**
   * Проверяем, попали ли в корабль игрока
   *
   * @param bulletList Список пуль
   */
  private void checkMainShipHit(final List<Bullet> bulletList) {
    for (Bullet bullet : bulletList) {
      if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
        continue;
      }
      if (mainShip.isBulletCollision(bullet)) {
        mainShip.damage(bullet.getDamage());
        bullet.destroy();
      }
    }
  }

  @Override
  public void resize(final Rect worldBounds) {
    mainShip.resize(worldBounds);
    backgroundSprite.resize(worldBounds);
    for (final BigAsteroidSprite bigAsteroid : bigAsteroids) bigAsteroid.resize(worldBounds);
    for (final MediumAsteroidSprite mediumAsteroid : mediumAsteroids) {
      mediumAsteroid.resize(worldBounds);
    }
    for (final SmallAsteroidSprite smallAsteroid : smallAsteroids) { smallAsteroid.resize(worldBounds); }
    gameOver.resize(worldBounds);
    newGame.resize(worldBounds);
  }

  @Override
  public void dispose() {
    batch.dispose();
    gameAtlas.dispose();
    bulletPool.dispose();
    enemyPool.dispose();
    bulletSound.dispose();
    explosionSound.dispose();
    mainShip.dispose();
    super.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    if (state == State.PLAYING) {
      return mainShip.keyDown(keycode);
    }
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (state == State.PLAYING) {
      return mainShip.keyUp(keycode);
    }
    return false;
  }

  @Override
  public boolean touchUp(final Vector2 touch,
                         final int pointer,
                         final int button) {
    newGame.touchUp(touch, pointer, button);
    return false;
  }

  @Override
  public boolean touchDown(final Vector2 touch,
                           final int pointer,
                           final int button) {
    if (state == State.PLAYING) {
      mainShip.touchDown(touch, pointer, button);
    }
    newGame.touchDown(touch, pointer, button);
    return false;
  }

  private enum State {PLAYING, PAUSE, GAME_OVER}
}
