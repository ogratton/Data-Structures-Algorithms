-- Author: Oliver Gratton
-- TODO auto-benchmarking (can be done in interactive with :set +s command)

module ListSorts where

import Data.List
import System.Random

-- BUBBLE SORT --
bsort xs = if (ys == xs) then xs else bsort ys 
                where ys = bsort' xs
bsort' (a:b:abs) = if (a<b) then a : bsort' (b:abs) else b : bsort' (a:abs)
bsort' s        = s

-- INSERTION SORT --
isort :: Ord a => [a] -> [a]
isort = foldr insert' []
insert' a []       = [a]
insert' a l@(x:xs) = if a < x then a:l else x : (insert' a xs)

-- SELECTION SORT --
ssort [] = []
ssort xs = let n = minimum xs; l = delete n xs in n : ssort l

-- MERGE SORT --
   -- v1: top-down
halve (x:xs) = let (h1,h2) = halve xs in ((x:h2),h1)
halve xs      = (xs,[])

merge list [] = list
merge [] list = list
merge l1@(x:xs) l2@(y:ys) | x <= y     = x : (merge xs l2)
                          | otherwise = y : (merge ys l1) 

msort []     = []
msort [x]    = [x]
msort xs     = merge (msort a) (msort b) where (a,b) = halve xs

   -- v2: bottom-up (from RosettaCode.org)
mergePairs (sorted1 : sorted2 : sorteds) = merge sorted1 sorted2 : mergePairs sorteds
mergePairs sorteds = sorteds
  
mergeAll [sorted] = sorted
mergeAll sorteds = mergeAll (mergePairs sorteds)

msort2 list = mergeAll (map (\x -> [x]) list)

-- QUICK SORT --
   -- v1: generators
qsort []     = []
qsort (x:xs) = (qsort lower) ++ [x] ++ (qsort higher)
                   where
                   lower  = [y | y <- xs, y <= x]
                   higher = [y | y <- xs, y > x]

   -- v2: filter                   
qsort2 []     = []
qsort2 (x:xs) = (qsort2 $ filter (<= x) xs) ++ [x] ++ (qsort2 $ filter (> x) xs)


-- BENCHMARKING --

   -- ordered case
test 0 f = length $ f [1..10000]
   -- reversed case
test 1 f = length $ f [10000,9999..1]
   -- random case
test n f = length $ f $ runRand n (randomList 10000)


   -- 'Random' code from Martín Escardo
data Rand a = Rand(StdGen -> (a , StdGen))

instance Monad Rand where
 return x = Rand(\g -> (x,g))
 Rand h >>= f = Rand(\g -> let (x, g')   = h g 
                               (Rand h') = f x
                           in h' g')

-- Remove the following to definitions if you have a version of
-- Haskell prior to the monad revolution:

instance Functor Rand where
  fmap f xm = xm >>= pure . f

instance Applicative Rand where
  pure = return
  xm <*> ym = xm >>= \x -> ym >>= pure . x

randInt :: Rand Int
randInt = Rand random

randomList :: Int -> Rand [Int]
randomList 0 = return []
randomList n = do  
  i <- randInt
  xs <- randomList(n-1)
  return(i : xs)
  
-- This is how we "can get out" of Rand a: 
runRand :: Int -> Rand a -> a
runRand seed (Rand h) = fst(h(mkStdGen seed))