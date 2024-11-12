package it.unibo.deathnote;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;




class TestDeathNote {
    private DeathNote deathNote = new DeathNoteImplementation();
    private static final String NAME1 = "Yagami";
    private static final String NAME2 = "Pippo";
    private static final String DEF_DEATH = "heart attack";
    private static final String OTHER_DEATH = "karting accident";
    private static final String CAUSE_DEATH = "Sassata nel naso";

    @Test
    public void ruleNumber() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->{
            deathNote.getRule(0);
        });
        assertTrue(exception.getMessage() != null);
        assertTrue(exception.getMessage() != "");

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            deathNote.getRule(-1);
        });
        assertTrue(exception.getMessage() != null);
        assertTrue(exception.getMessage() != "");
    }

    @Test
    public void rulesNotEmpty() {
        for (String rule : DeathNote.RULES) {
            assertTrue(rule != null);
            assertTrue(rule != "");
        }
    }

    @Test
    public void humanDies() {
        assertFalse(this.deathNote.isNameWritten(NAME1));
        this.deathNote.writeName(NAME1);
        assertTrue(this.deathNote.isNameWritten(NAME1));
        assertTrue(this.deathNote.getNamesWritten() == 1);
        assertFalse(this.deathNote.isNameWritten(""));        
    }

    @Test
    public void causeOfDeath() throws InterruptedException {
        deathNote = new DeathNoteImplementation();

        assertThrows(IllegalStateException.class, () -> deathNote.writeDeathCause(NAME1));
        deathNote.writeName(NAME1);
        assertTrue(deathNote.getDeathCause(NAME1) == DEF_DEATH);
        deathNote.writeName(NAME2);
        deathNote.writeDeathCause(OTHER_DEATH);
        assertTrue(deathNote.getDeathCause(NAME2) == OTHER_DEATH);

        Thread.sleep(100);
        deathNote.writeDeathCause("suicidio");
        assertTrue(deathNote.getDeathCause(NAME2) == OTHER_DEATH);
    }

    @Test
    public void deathDetails() throws InterruptedException {
        deathNote = new DeathNoteImplementation();

        assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(CAUSE_DEATH));
        deathNote.writeName(NAME1);
        assertTrue(deathNote.getDeathDetails(NAME1) == null);
        deathNote.writeDetails(CAUSE_DEATH);
        assertTrue(deathNote.getDeathDetails(NAME1) == CAUSE_DEATH);
        deathNote.writeName(NAME2);
        Thread.sleep(6100);
        deathNote.writeDetails(CAUSE_DEATH);
        assertTrue(deathNote.getDeathDetails(NAME2) == null);
    }
}