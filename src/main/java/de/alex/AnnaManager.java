/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package de.alex;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;



/**
 * The {@link annaManager} receives various events and intents and manages the flow of the
 * game.
 */
@Component
public class AnnaManager {
 

    /**
     * Creates and returns response for Launch request.
     *
     * @param request
     *            {@link LaunchRequest} for this request
     * @param session
     *            Speechlet {@link Session} for this request
     * @return response for launch request
     */
    public SpeechletResponse getLaunchResponse(LaunchRequest request, Session session) {
        // Speak welcome message and ask user questions
        
        String speechText, repromptText;
        

  
        speechText = "Guten morgen Anna, wie hast du geschalfen?";
        repromptText = "Wie hast du geschlafen?";
       

        return getAskSpeechletResponse(speechText, repromptText);
    }

    /**
     * Creates and returns response for the new game intent.
     *
     * @param session
     *            {@link Session} for the request
     * @param skillContext
     *            {@link SkillContext} for this request
     * @return response for the new game intent.
     */
    public SpeechletResponse getSchlechteNachtIntentResponse(Session session) {

        return getAskSpeechletResponse("Das ist ja echt doof."," Musst du heute arbeiten?");
        
    }

    public SpeechletResponse getGuteNachtIntentResponse(Session session) {
        return getAskSpeechletResponse("Das freut mich!","Musst du heute arbeiten?");
    }

    public SpeechletResponse getArbeitenResponse(Session session) {
        return getTellSpeechletResponse("Ich hoffe du hast viele nette Kunden!");
    }

    public SpeechletResponse getNichtArbeitenResponse(Session session) {
        return getTellSpeechletResponse("Schön, dann kannst du entspannen!");
    }

    /**
     * Creates and returns response for the add player intent.
     *
     * @param intent
     *            {@link Intent} for this request
     * @param session
     *            Speechlet {@link Session} for this request
     * @param skillContext
     * @return response for the add player intent.
     */
    // public SpeechletResponse getAddPlayerIntentResponse(Intent intent, Session session,
    //         SkillContext skillContext) {
    //     // add a player to the current game,
    //     // terminate or continue the conversation based on whether the intent
    //     // is from a one shot command or not.
    //     String newPlayerName =
    //             ScoreKeeperTextUtil.getPlayerName(intent.getSlot(SLOT_PLAYER_NAME).getValue());
    //     if (newPlayerName == null) {
    //         String speechText = "OK. Who do you want to add?";
    //         return getAskSpeechletResponse(speechText, speechText);
    //     }

    //     // Load the previous game
    //     ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);
    //     if (game == null) {
    //         game = ScoreKeeperGame.newInstance(session, ScoreKeeperGameData.newInstance());
    //     }

    //     game.addPlayer(newPlayerName);

    //     // Save the updated game
    //     scoreKeeperDao.saveScoreKeeperGame(game);

    //     String speechText = newPlayerName + " has joined your game. ";
    //     String repromptText = null;

    //     if (skillContext.needsMoreHelp()) {
    //         if (game.getNumberOfPlayers() == 1) {
    //             speechText += "You can say, I am done adding players. Now who's your next player?";

    //         } else {
    //             speechText += "Who is your next player?";
    //         }
    //         repromptText = ScoreKeeperTextUtil.NEXT_HELP;
    //     }

    //     if (repromptText != null) {
    //         return getAskSpeechletResponse(speechText, repromptText);
    //     } else {
    //         return getTellSpeechletResponse(speechText);
    //     }
    // }

    /**
     * Creates and returns response for the add score intent.
     *
     * @param intent
     *            {@link Intent} for this request
     * @param session
     *            {@link Session} for this request
     * @param skillContext
     *            {@link SkillContext} for this request
     * @return response for the add score intent
     */
    // public SpeechletResponse getAddScoreIntentResponse(Intent intent, Session session,
    //         SkillContext skillContext) {
    //     String playerName =
    //             ScoreKeeperTextUtil.getPlayerName(intent.getSlot(SLOT_PLAYER_NAME).getValue());
    //     if (playerName == null) {
    //         String speechText = "Sorry, I did not hear the player name. Please say again?";
    //         return getAskSpeechletResponse(speechText, speechText);
    //     }

    //     int score = 0;
    //     try {
    //         score = Integer.parseInt(intent.getSlot(SLOT_SCORE_NUMBER).getValue());
    //     } catch (NumberFormatException e) {
    //         String speechText = "Sorry, I did not hear the points. Please say again?";
    //         return getAskSpeechletResponse(speechText, speechText);
    //     }

    //     ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);
    //     if (game == null) {
    //         return getTellSpeechletResponse("A game has not been started. Please say New Game to "
    //                 + "start a new game before adding scores.");
    //     }

    //     if (game.getNumberOfPlayers() == 0) {
    //         String speechText = "Sorry, no player has joined the game yet. What can I do for you?";
    //         return getAskSpeechletResponse(speechText, speechText);
    //     }

    //     // Update score
    //     if (!game.addScoreForPlayer(playerName, score)) {
    //         String speechText = "Sorry, " + playerName + " has not joined the game. What else?";
    //         return getAskSpeechletResponse(speechText, speechText);
    //     }

    //     // Save game
    //     scoreKeeperDao.saveScoreKeeperGame(game);

    //     // Prepare speech text. If the game has less than 3 players, skip reading scores for each
    //     // player for brevity.
    //     String speechText = score + " for " + playerName + ". ";
    //     if (game.getNumberOfPlayers() > MAX_PLAYERS_FOR_SPEECH) {
    //         speechText += playerName + " has " + game.getScoreForPlayer(playerName) + " in total.";
    //     } else {
    //         speechText += getAllScoresAsSpeechText(game.getAllScoresInDescndingOrder());
    //     }

    //     return getTellSpeechletResponse(speechText);
    // }

    // /**
    //  * Creates and returns response for the tell scores intent.
    //  *
    //  * @param intent
    //  *            {@link Intent} for this request
    //  * @param session
    //  *            {@link Session} for this request
    //  * @return response for the tell scores intent
    //  */
    // public SpeechletResponse getTellScoresIntentResponse(Intent intent, Session session) {
    //     // tells the scores in the leaderboard and send the result in card.
    //     ScoreKeeperGame game = scoreKeeperDao.getScoreKeeperGame(session);

    //     if (game == null || !game.hasPlayers()) {
    //         return getTellSpeechletResponse("Nobody has joined the game.");
    //     }

    //     SortedMap<String, Long> sortedScores = game.getAllScoresInDescndingOrder();
    //     String speechText = getAllScoresAsSpeechText(sortedScores);
    //     Card leaderboardScoreCard = getLeaderboardScoreCard(sortedScores);

    //     PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    //     speech.setText(speechText);

    //     return SpeechletResponse.newTellResponse(speech, leaderboardScoreCard);
    // }

    // /**
    //  * Creates and returns response for the reset players intent.
    //  *
    //  * @param intent
    //  *            {@link Intent} for this request
    //  * @param session
    //  *            {@link Session} for this request
    //  * @return response for the reset players intent
    //  */
    // public SpeechletResponse getResetPlayersIntentResponse(Intent intent, Session session) {
    //     // Remove all players
    //     ScoreKeeperGame game =
    //             ScoreKeeperGame.newInstance(session, ScoreKeeperGameData.newInstance());
    //     scoreKeeperDao.saveScoreKeeperGame(game);

    //     String speechText = "New game started without players. Who do you want to add first?";
    //     return getAskSpeechletResponse(speechText, speechText);
    // }

    /**
     * Creates and returns response for the help intent.
     *
     * @param intent
     *            {@link Intent} for this request
     * @param session
     *            {@link Session} for this request
     * @param skillContext
     *            {@link SkillContext} for this request
     * @return response for the help intent
     */
    public SpeechletResponse getHelpIntentResponse(Intent intent, Session session) {
        return  getAskSpeechletResponse("Du kannst einfach mit mir quatschen, Sage einfach: Was willst du wissen Alexa?","Du kannst einfach mit mir quatschen, Sage einfach: Was willst du wissen Alexa?");
    }

    /**
     * Creates and returns response for the exit intent.
     *
     * @param intent
     *            {@link Intent} for this request
     * @param session
     *            {@link Session} for this request
     * @param skillContext
     *            {@link SkillContext} for this request
     * @return response for the exit intent
     */
    public SpeechletResponse getExitIntentResponse(Intent intent, Session session) {
        return getTellSpeechletResponse("Bis dann");
    }

    /**
     * Returns an ask Speechlet response for a speech and reprompt text.
     *
     * @param speechText
     *            Text for speech output
     * @param repromptText
     *            Text for reprompt output
     * @return ask Speechlet response for a speech and reprompt text
     */
    private SpeechletResponse getAskSpeechletResponse(String speechText, String repromptText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Session");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Returns a tell Speechlet response for a speech and reprompt text.
     *
     * @param speechText
     *            Text for speech output
     * @return a tell Speechlet response for a speech and reprompt text
     */
    private SpeechletResponse getTellSpeechletResponse(String speechText) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Session");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

}
