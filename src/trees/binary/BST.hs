-- Author: Oliver Gratton
-- BST with value and frequency as node

import Data.Maybe

{- Constructor and contruction methods -}
data BST c =   Empty 
             | Branch (BST c) (BST c) (c,Int)
    deriving (Show, Eq, Ord, Read)
    
-- addOne
addOne :: (Ord c) => c -> BST c -> BST c
addOne i Empty = Branch Empty Empty (i,1)
addOne i (Branch left right a@(j,m))
    | i<j       = Branch (addOne i left) right a
	| i>j       = Branch left (addOne i right) a
	| otherwise = Branch left right (j,m+1)

-- addMany
addMany :: (Ord c) => [c] -> BST c -> BST c
addMany []     t = t
addMany (x:xs) t = addMany xs (addOne x t)

-- makeBST
makeBST :: (Ord c) => [c] -> BST c
makeBST = (flip addMany) Empty

{- Simple enquiry methods -}
-- isEmpty
isEmpty :: BST c -> Bool
isEmpty Empty = True
isEmpty _     = False

-- size
size :: BST c -> Int
size Empty                 = 0
size (Branch left right _) = 1 + (size left) + (size right)

-- height
height :: BST c -> Int
height Empty                 = -1
height (Branch left right _) = 1 + max (height left) (height right)

-- UNSAFE find

-- UNSAFE key

-- UNSAFE value

-- UNSAFE smallest

-- UNSAFE largest

-- UNSAFE left

-- UNSAFE right

-- toList
toList :: BST c -> [c]
toList  Empty                   = []
toList (Branch left right node) = (toList left) ++ (uncurry $ flip $ replicate) node ++ (toList right)

-- has
has :: (Ord c) => BST c -> c -> Bool
has Empty _ = False									 
has (Branch left right (i,_)) e
    | e<i = has left e
	| e>i = has right e
	| otherwise = True
								  

{- Tree-mutating methods -}
-- deleteSmallest

-- deleteLargest

-- delete

-- balance

{- SORT -}
-- treeSort
treeSort :: (Ord c) => [c] -> [c]
treeSort = toList . makeBST