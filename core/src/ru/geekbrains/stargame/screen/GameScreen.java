package ru.geekbrains.stargame.screen;

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
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.BigAsteroidSprite;
import ru.geekbrains.stargame.sprites.MainShip;
import ru.geekbrains.stargame.sprites.MediumAsteroidSprite;
import ru.geekbrains.stargame.sprites.SmallAsteroidSprite;
import ru.geekbrains.stargame.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
  private static final int BIG_ASTEROID_COUNT   = 5;
  private static final int MED_ASTEROID_COUNT   = 7;
  private static final int SMALL_ASTEROID_COUNT = 10;

  private TextureAtlas           gameAtlas;
  private MainShip               mainShip;
  private EnemyPool              enemyPool;
  private EnemyEmitter           enemyEmitter;
  private BackgroundSprite       backgroundSprite;
  private BigAsteroidSprite[]    bigAsteroids;
  private MediumAsteroidSprite[] mediumAsteroids;
  private SmallAsteroidSprite[]  smallAsteroids;
  private BulletPool             bulletPool;
  private Sound                  laserSound;
  private Sound                  bulletSound;

  @Override
  public void show() {
    super.show();
    gameAtlas = new TextureAtlas(Gdx.files.internal("textures/StarGame.atlas"));

    laserSound  = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
    bulletSound = Gdx.audio.newSound(Gdx.files.internal("sound/bullet.wav"));

    bulletPool   = new BulletPool();
    enemyPool    = new EnemyPool(bulletPool, worldBounds);
    enemyEmitter = new EnemyEmitter(gameAtlas, enemyPool, worldBounds, bulletSound);

    initSprites();
  }

  private void initSprites() {
    try {
      mainShip         = new MainShip(gameAtlas, bulletPool, laserSound);
      backgroundSprite = new BackgroundSprite(gameAtlas);
      bigAsteroids     = new BigAsteroidSprite[BIG_ASTEROID_COUNT];
      mediumAsteroids  = new MediumAsteroidSprite[MED_ASTEROID_COUNT];
      smallAsteroids   = new SmallAsteroidSprite[SMALL_ASTEROID_COUNT];

      for (int i = 0; i < BIG_ASTEROID_COUNT; i++)
           bigAsteroids[i] = new BigAsteroidSprite(gameAtlas);

      for (int i = 0; i < MED_ASTEROID_COUNT; i++)
           mediumAsteroids[i] = new MediumAsteroidSprite(gameAtlas);

      for (int i = 0; i < SMALL_ASTEROID_COUNT; i++)
           smallAsteroids[i] = new SmallAsteroidSprite(gameAtlas);

    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    update(delta);
    freeAllDestroyed();
    draw();
  }

  private void update(final float delta) {
    mainShip.update(delta);
    for (final BigAsteroidSprite asteroidSprite : bigAsteroids) asteroidSprite.update(delta);
    for (final MediumAsteroidSprite mediumAsteroidSprite : mediumAsteroids)
      mediumAsteroidSprite.update(delta);
    for (final SmallAsteroidSprite smallAsteroidSprite : smallAsteroids)
      smallAsteroidSprite.update(delta);
    bulletPool.updateActiveSprites(delta);
    enemyPool.updateActiveSprites(delta);
    enemyEmitter.generate(delta);
  }

  public void freeAllDestroyed() {
    bulletPool.freeAllDestroyedActiveObjects();
    enemyPool.freeAllDestroyedActiveObjects();
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    for (final SmallAsteroidSprite smallAsteroidSprite : smallAsteroids)
      smallAsteroidSprite.draw(batch);
    for (final MediumAsteroidSprite mediumAsteroidSprite : mediumAsteroids)
      mediumAsteroidSprite.draw(batch);
    for (final BigAsteroidSprite asteroidSprite : bigAsteroids) asteroidSprite.draw(batch);
    bulletPool.drawActiveSprites(batch);
    enemyPool.drawActiveSprites(batch);
    mainShip.draw(batch);
    batch.end();
  }

  @Override
  public void resize(final Rect worldBounds) {
    mainShip.resize(worldBounds);
    backgroundSprite.resize(worldBounds);
    for (final BigAsteroidSprite asteroidSprite : bigAsteroids) asteroidSprite.resize(worldBounds);
    for (final MediumAsteroidSprite mediumAsteroidSprite : mediumAsteroids)
      mediumAsteroidSprite.resize(worldBounds);
    for (final SmallAsteroidSprite smallAsteroidSprite : smallAsteroids)
      smallAsteroidSprite.resize(worldBounds);
  }

  @Override
  public void dispose() {
    batch.dispose();
    gameAtlas.dispose();
    bulletPool.dispose();
    enemyPool.dispose();
    bulletSound.dispose();
    mainShip.dispose();
    super.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    return mainShip.keyDown(keycode);
  }

  @Override
  public boolean keyUp(int keycode) {
    return mainShip.keyUp(keycode);
  }

  @Override
  public boolean touchDown(final Vector2 touch,
                           final int pointer,
                           final int button) {
    mainShip.touchDown(touch, pointer, button);
    return false;
  }
}
