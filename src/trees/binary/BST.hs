-- Author: Oliver Gratton
-- BST with value and frequency as node

-- TODO do with Maybe and Just to be safe

data BST c =   Leaf c Int 
             | Branch (BST c) (BST c) Int
    deriving (Show, Eq, Ord, Read)
	
-- addOne

-- addMany

-- makeBST

-- size

-- height

-- smallest

-- largest

-- toList

-- has

-- find

-- deleteSmallest

-- deleteLargest

-- delete

-- left

-- right

-- balance

-- treeSort