package com.example.fightingflow.ui.comboDisplayScreen.inputConverter

import com.example.fightingflow.data.datastore.Console
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.model.MoveEntry
import timber.log.Timber

fun convertInputsToConsole(
    move: MoveEntry,
    game: Game?,
    console: Console?,
    classic: Boolean = false
): MoveEntry {

    Timber.d("Converting ${move.moveName} to $console for $game input...")

    console?.let { outerConsole ->
        game?.let { innerGame ->
            return when (outerConsole) {
                Console.STANDARD -> move

                Console.PLAYSTATION -> {
                    Timber.d("Getting $console input")
                    when (innerGame) {
                        Game.T8 -> {
                            Timber.d("Getting $game input...")
                            tekkenToPlaystationInputs(move)
                        }
                        Game.SF6 -> {
                            Timber.d("Getting $game input...")
                            if (classic) {
                                Timber.d("Getting classic input...")
                                sf6ClassicToPlaystationInputs(move)
                            } else {
                                Timber.d("Getting modern input...")
                                sf6ModernToPlaystationInputs(move)
                            }
                        }
                        Game.MK1 -> {
                            Timber.d("Getting $game input...")
                            mk1ToPlaystation(move)
                        }
                    }
                }

                Console.XBOX -> {
                    Timber.d("Getting $console input")
                    when (innerGame) {
                        Game.T8 -> {
                            Timber.d("Getting $game input...")
                            tekkenToXboxInputs(move)
                        }
                        Game.SF6 -> {
                            if (classic) {
                                Timber.d("Getting classic input...")
                                sf6ClassicToXboxInputs(move)
                            } else {
                                Timber.d("Getting classic input...")
                                sf6ModernToXboxInputs(move)
                            }
                        }
                        Game.MK1 -> {
                            Timber.d("Getting $game input...")
                            mk1ToXbox(move)
                        }
                    }
                }

                Console.NINTENDO -> {
                    Timber.d("Getting $console input")
                    when (innerGame) {
                        Game.T8 -> {
                            Timber.d("Getting $game input...")
                            tekkenToNintendoInputs(move)
                        }
                        Game.SF6 -> {
                            Timber.d("Getting $game input...")
                            if (classic) {
                                Timber.d("Getting classic input...")
                                sf6ClassicToNintendoInputs(move)
                            } else {
                                Timber.d("Getting modern input...")
                                sf6ModernToNintendoInputs(move)
                            }
                        }
                        Game.MK1 -> {
                            Timber.d("Getting $game input...")
                            mk1ToNintendo(move)
                        }
                    }
                }
            }
        }
    }
    return move
}

fun convertInputToStandard(
    move: MoveEntry,
    game: Game?,
    console: Console?,
    classic: Boolean = false
): MoveEntry {
    Timber.d("Converting $move to standard...")
    Timber.d("${console}, $game")
    console?.let { outerConsole ->
        game?.let { innerGame ->
            return when (outerConsole) {
                Console.STANDARD -> move

                Console.PLAYSTATION -> {
                    Timber.d("Converting $console input...")
                    when (innerGame) {
                        Game.MK1 -> {
                            Timber.d("Getting $game input..")
                            playStationToMK1(move)
                        }
                        Game.SF6 -> {
                            Timber.d("Getting $game input..")
                            when (classic) {
                                true -> {
                                    Timber.d("Getting classic input")
                                    playstationToSF6Classic(move)
                                }

                                false -> {
                                    Timber.d("Getting modern input")
                                    playstationToSF6Modern(move)
                                }
                            }
                        }

                        Game.T8 -> {
                            Timber.d("Getting $game input..")
                            Timber.d("Converting $console input...")
                            playStationToT8(move)
                        }
                    }
                }

                Console.XBOX -> {
                    Timber.d("Converting $console input...")
                    when (innerGame) {
                        Game.MK1 -> {
                            Timber.d("Getting $game input..")
                            xboxToMK1(move)
                        }
                        Game.SF6 -> {
                            Timber.d("Getting $game input..")
                            when (classic) {
                                true -> {
                                    Timber.d("Getting classic input")
                                    xboxToSF6Classic(move)
                                }

                                false -> {
                                    Timber.d("Getting modern input")
                                    xboxToSF6Modern(move)
                                }
                            }
                        }
                        Game.T8 -> {
                            Timber.d("Getting $game input..")
                            xboxToT8(move)
                        }
                    }
                }

                Console.NINTENDO -> {
                    Timber.d("Converting $console input...")
                    when (innerGame) {
                        Game.MK1 -> {
                            Timber.d("Getting $game input..")
                            nintendoToMK1(move)
                        }
                        Game.SF6 -> {
                            Timber.d("Getting $game input..")
                            when (classic) {
                                true -> {
                                    Timber.d("Getting classic input")
                                    nintendoToSF6Classic(move)
                                }
                                false -> {
                                    Timber.d("Getting modern input")
                                    nintendoToSF6Modern(move)
                                }
                            }
                        }
                        Game.T8 -> {
                            Timber.d("Getting $game input..")
                            nintendoToT8(move)
                        }
                    }
                }
            }
        }
    }
    return move
}