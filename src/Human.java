import java.awt.*;
import java.io.Serializable;

public class Human extends Animal implements Serializable
{
    protected boolean abilityActiveFlag = false;
    protected int abilityDurationCounter = 0;
    protected int abilityCooldownCounter = 0;
    protected int DurationCountdown = Constants.HUMAN_ABILITY_DURATION;
    private Direction nextDirection;

    public Human (int x, int y, World world)
    {
        super(Constants.HUMAN_STR, Constants.HUMAN_INIT, x, y, Constants.HUMAN_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Human(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.HUMAN_COLOR;
    }

    @Override
    public void move()
    {
        if (nextDirection != null)
        {
            moveWithInput(nextDirection);
            nextDirection = null;
        }
    }

    public void setNextDirection(Direction direction)
    {
        this.nextDirection = direction;
    }

    public void moveWithInput(Direction direction)
    {
        int newX = getX();
        int newY = getY();

        // ========== movements [input] ========================
        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                 newX++;
                 break;
            case ACTIVATE_ABILITY:
            {
                //if ability is active do nothing
                if (abilityActiveFlag)
                {
                    return;
                }
                //if cooldown has passed, activate ability
                if (abilityCooldownCounter == 0)
                {
                    world.log("[\uD83D\uDEE1\uFE0Fimmortality activated\uD83D\uDEE1\uFE0F]");
                    abilityActiveFlag = true;
                    abilityCooldownCounter = 5;
                    return;
                }
                world.log("[❌COOLDOWN, CANNOT ACTIVATE❌]");
                break;

            }
        }

        // ============ ability management ==================
        if (abilityActiveFlag)
        {
            world.log("[immortality turn: " + abilityDurationCounter + "]");
            abilityDurationCounter++;
            if (abilityDurationCounter >= 5)
            {
                world.log("[immortality END]");
                abilityActiveFlag = false;
                abilityDurationCounter = 0;
            }
        }
        else if (abilityCooldownCounter > 0)
        {
            abilityCooldownCounter--;
            world.log("[Cooldown left: " + abilityCooldownCounter + "]");
        }

        // ======== movement [updating data] ============
        if (world.isInside(newX, newY))
        {
            Organism other = world.getOrganismAt(newX, newY);
            if (other == null)
            {
                world.moveOrganism(this, newX, newY);
                world.log("[human move]");
            }
            else
            {
                // don't attack with active immortality
                if (abilityActiveFlag && this.getStrength() < other.getStrength())
                {
                    Point escape = world.findFreeNeighbor(getX(), getY());
                    if (escape != null && world.isInside(escape.x, escape.y))
                    {
                        world.moveOrganism(this, escape.x, escape.y);
                        world.log("[immortality dodge]");
                        return;
                    }
                }
                other.collision(this);
            }
        }
    }

    @Override
    public void collision(Organism attacker)
    {
        if (abilityActiveFlag)
        {
            Point escape = world.findFreeNeighbor(getX(), getY());
            if (escape != null && world.isInside(escape.x, escape.y)) {
                world.moveOrganism(attacker, getX(), getY());
                world.moveOrganism(this, escape.x, escape.y);
                world.log("[human escaped from attacker!] " + attacker.getLook());
                return;
            }
        }
        super.collision(attacker);
    }

    public boolean getAbilityFlag() {
        return abilityActiveFlag;
    }

    public int getAbilityDurationCounter() {
        return abilityDurationCounter;
    }

    public int getAbilityCooldownCounter() {
        return abilityCooldownCounter;
    }

    public void setAbilityFlag(boolean value) {
        abilityActiveFlag = value;
    }

    public void setAbilityDurationCounter(int value) {
        abilityDurationCounter = value;
    }

    public void setAbilityCooldownCounter(int value) {
        abilityCooldownCounter = value;
    }

}
