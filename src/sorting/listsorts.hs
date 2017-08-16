-- Author: Oliver Gratton
-- TODO auto-benchmarking (can be done in interactive with :set +s command)
import Data.List

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
-- TODO

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