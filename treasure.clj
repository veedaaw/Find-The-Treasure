;; Solving "Finding The Treasure" problem using Backtracking algorithm.
;; @author Vida Abdollahi - 40039052

(ns treasure
  (:gen-class))

;; This function just read the map.txt file and count the number of characters within it, including \n characters.
;;********* NOTE: please ensure that each row in the map.txt file has a \n at the end otherwise it won't work! *********;;

(defn readFile []
  (def mapFile (slurp "map.txt"))
  (def countLine (count mapFile))
  )
(readFile)

;; Split spaces between characters and turn the map into a 1d vector

(defn splitString []

  (def mapArray (clojure.string/split mapFile #""))
  (def row (clojure.string/split mapFile #"\n"))
  (def countRow (count row))
  (def col (/ countLine countRow))
  (def solutionArray (vec mapArray))
  (println "This is my challenge: ")
  (println mapArray)
  )

(splitString)

;; Here we just check to see whether the next step is a safe place to go! \n considered to be not safe.

(defn isSafeToGo [xCoor array]

  (def x (and (>= xCoor 0) (< xCoor countLine) (not= (str (get array xCoor)) "#") (not= (str (get array xCoor)) "\n") ))
  (cond
    (= x true) true
    (= x false) false
    :else (println "xCoord is not defined")))

;; Find the path based on the current position using this recursive function [aka backtracking]
;; It's important to keep track of the directions or we will be trapped in a loop!

(defn findPath [arr xCoor solution direction]
  (if (isSafeToGo xCoor mapArray)
    (do
      (if (= (str (get arr xCoor)) "@")
        (do
          (println "Woo hoo, I found the treasure :-)")
          true)
        (do
          (def solutionArray (assoc solutionArray xCoor "+"))
          (if (and (not= direction "up") (findPath arr (+ xCoor col) solutionArray "down"))
            true
            (if (and (not= direction "left") (findPath arr (+ xCoor 1) solutionArray "right"))
              true
              (if (and (not= direction "down") (findPath arr (- xCoor col) solutionArray "up"))
                true
                (if (and (not= direction "right") (findPath arr (- xCoor 1) solutionArray "left"))
                  true
                  (do
                    (def solutionArray (assoc solutionArray xCoor "!"))
                    false
                    )
                  )

                )
              )
            )

          )
        )

      )

    false)

  )
;; This is the main function, we start the maze with going down by default.

(defn play [arr]

  (if (findPath arr 0 solutionArray "down")
    (do
      true
      )

    (if (= (findPath arr 0 solutionArray "down") false)
      (do
        false
        (println "Uh oh, I could not find the treasure :-(")
        )
      (println "oops"))
    )
  )

(play mapArray)
(println solutionArray)