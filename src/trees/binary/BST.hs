-- Author: Oliver Gratton
-- BST with value and frequency as node

import Data.Maybe

{- Constructor and contruction methods -}
data BST c =   Empty 
             | Branch (BST c) (BST c) (c,Int)
    deriving (Show, Eq, Ord, Read)
    
-- add one element
addOne :: (Ord c) => c -> BST c -> BST c
addOne i Empty = Branch Empty Empty (i,1)
addOne i (Branch left right a@(j,m))
    | i<j       = Branch (addOne i left) right a
    | i>j       = Branch left (addOne i right) a
    | otherwise = Branch left right (j,m+1)

-- add multiple elements
addMany :: (Ord c) => [c] -> BST c -> BST c
addMany []     t = t
addMany (x:xs) t = addMany xs (addOne x t)

-- make a bst straight from list
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

-- height (Empty -1 by convention)
height :: BST c -> Int
height Empty                 = -1
height (Branch left right _) = 1 + max (height left) (height right)

-- UNSAFE find frequency of key
find :: (Ord c) => c -> BST c -> Maybe Int
find _  Empty = Nothing
find x (Branch left right (y,n)) | x<y = find x left
                                 | x>y = find x right
                                 | otherwise = Just n

-- UNSAFE get key of branch
key :: (Ord c) => BST c -> Maybe c
key  Empty             = Nothing
key (Branch _ _ (a,_)) = Just a

-- UNSAFE get value of branch
value :: (Ord c) => BST c -> Maybe Int
value  Empty             = Nothing
value (Branch _ _ (_,b)) = Just b

-- UNSAFE smallest node in tree
smallest :: BST c -> Maybe (c,Int)
smallest  Empty            = Nothing
smallest (Branch left _ a) = if isEmpty left then Just a else smallest left

-- UNSAFE largest node in tree
largest :: BST c -> Maybe (c,Int)
largest  Empty             = Nothing
largest (Branch _ right a) = if isEmpty right then Just a else largest right

-- UNSAFE left branch
left Empty          = Nothing
left (Branch l _ _) = Just l

-- UNSAFE right branch
right Empty          = Nothing
right (Branch _ r _) = Just r

-- toList
toList :: BST c -> [c]
toList t = concatMap (uncurry $ flip $ replicate) (toHisto t)

-- toHisto
toHisto :: BST c -> [(c,Int)]
toHisto Empty                   = []
toHisto (Branch left right node) = (toHisto left) ++ [node] ++ (toHisto right)

-- has
has :: (Ord c) => BST c -> c -> Bool
has Empty _ = False                                     
has (Branch left right (i,_)) e
    | e<i = has left e
    | e>i = has right e
    | otherwise = True                            

{- Tree-mutating methods -}
-- deleteSmallest
deleteSmallest :: BST c -> BST c
deleteSmallest  Empty                = Empty
deleteSmallest (Branch left right a) | isEmpty right = left
                                     | otherwise     = Branch (deleteSmallest left) right a 

-- deleteLargest
deleteLargest :: BST c -> BST c
deleteLargest  Empty                = Empty
deleteLargest (Branch left right a) | isEmpty right = left
                                    | otherwise     = Branch left (deleteLargest right) a 
                                     
-- delete
delete :: (Ord c) => c -> BST c -> BST c
delete x Empty = Empty
delete x (Branch left right a@(y,_)) | x<y = Branch (delete x left) right a
                                     | x>y = Branch left (delete x right) a
                                     | otherwise = if isEmpty left then right else 
                                                   if isEmpty right then left
                                                   else Branch (deleteLargest left) right (fromJust $ largest left)

-- balance an existing tree
balance :: (Ord c) => BST c -> BST c
balance = bal . toHisto

-- make a balanced tree from a sorted histogram
bal :: (Ord c) => [(c,Int)] -> BST c
bal [] = Empty
bal xs = let (left,node,right) = thirds xs in Branch (bal left) (bal right) (head node)

  -- inefficient but hey
thirds :: (Ord c) => [c] -> ([c],[c],[c]) 
thirds [] = ([],[],[])
thirds xs = (take mid xs,[xs !! mid],drop (mid+1) xs) where mid = (length xs) `div` 2

{- SORT -}
-- treeSort
--treeSort :: (Ord c) => [c] -> [c]
--treeSort = toList . makeBST