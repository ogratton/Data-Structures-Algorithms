// Author: Oliver Gratton
// a bst of ints with a count of how many times that number has been inserted

type node = int * int (* would like to make that first int 'a but I can't for some reason :( *)
type bst = 
    | Empty
    | BST of (node * bst * bst)

(* insert a node into a BST *)
let rec insert a t =
    match t with
    |Empty                          -> BST((a,1),Empty,Empty)
    |BST((x,y),left,right) when a=x -> BST((x,(y+1)),left,right)
    |BST((x,y),left,right) when a<x -> BST((x,y),(insert a left),right)
    |BST((x,y),left,right)          -> BST((x,y),left,(insert a right))

(* insert a list of items at once to an existing tree *)
let rec addItems list tree =
    match list with
    |[]    -> tree
    |x::xs -> addItems xs (insert x tree)

(* make a new BST from a list *)
let make_bst list = addItems list Empty

(* nubmer of nodes in the tree *)
let rec size = function
    |Empty                -> 0
    |BST(node,left,right) -> 1 + (size left) + (size right)

(* height of the tree *)
let rec height = function
    |Empty                -> -1
    |BST(node,left,right) -> 1 + max (height left) (height right)

(* save to list *)
let rec printBST = function
    |Empty                 -> []
    |BST((x,y),left,right) -> (printBST left) @ ((x,y)::printBST right)

(* true if empty *)
let isEmpty = function
    |Empty -> true
    |_     -> false

(* does the bst contain this node value? *)
let rec has a = function
    |Empty                          -> false
    |BST((x,y),left,right) when a=x -> true
    |BST((x,y),left,right) when a<x -> has a left
    |BST((x,y),left,right)          -> has a right

(* return the quantity of a specific node *)
let rec find a = function
    |Empty                          -> None
    |BST((x,y),left,right) when a=x -> Some y
    |BST((x,y),left,right) when a<x -> find a left
    |BST((x,y),left,right)          -> find a right

(* get the key of the node *)
let key = function
    |Empty                 -> None
    |BST((x,y),left,right) -> Some x

(* get the value of the node *)
let value = function
    |Empty                 -> None
    |BST((x,y),left,right) -> Some y

(* return the node with the largest key *)
let rec largest = function
    |Empty                 -> None
    |BST((x,y),left,right) -> if isEmpty right then Some (x,y) else largest right

(* delete the largest item in a tree *)
let rec deleteLargest = function
    |Empty                 -> Empty
    |BST((x,y),left,right) -> if isEmpty right then  left else BST((x,y),left,(deleteLargest right))
    

(* delete a node from the tree *)
let rec delete a = function
    |Empty                          -> Empty
    |BST((x,y),left,right) when a=x -> if isEmpty left then right else if isEmpty right then left else BST((fst (Option.get (largest left)), snd (Option.get (largest left))),deleteLargest left, right)
    |BST((x,y),left,right) when a<x -> BST((x,y),delete a left, right)
    |BST((x,y),left,right)          -> BST((x,y),left, delete a right)

(* return the left subtree *)
let left = function
    |Empty                 -> None
    |BST((x,y),left,right) -> Some left

(* return the right subtree *)
let right = function
    |Empty                 -> None
    |BST((x,y),left,right) -> Some right


(* split a list into the left half, middle item and right half *)
let listInfo list =
    let rec mid n acc = function
        |x::xs when n=0 -> List.rev acc, x, xs
        |x::xs when n>0 -> mid (n-1) (x::acc) xs
        |_ -> failwith "wtf"
    in mid ((List.length list)/2) [] list

(* access info in triples *)
let fstT = function
    |x,_,_ -> x
let sndT = function
    |_,y,_ -> y
let thdT = function
    |_,_,z -> z

(* balance a tree *)
let balance tree =
    let rec bal list =
        if list=[] then Empty else
        let info = listInfo list in
        BST( (fst (sndT info),snd (sndT info)) , bal (fstT info), bal (thdT info))
    in bal (printBST tree)
    

(* expand histograms back to lists *)
let expandHisto list =
    let rec many acc n x =
      if n = 0 then acc else many (x :: acc) (n-1) x in
    let rec aux acc = function
      | [] -> acc
      | (x,n) :: t -> aux (many acc n x) t  in
    aux [] (List.rev list);;


(* TREE SORT *)
(* looks like magic, but the making of the tree itself does the sorting *)
let tree_sort list = expandHisto (printBST(make_bst list))

(* make a random list to be sorted *)
let mk size upper_bound =
    let rnd = System.Random()
    List.init size (fun _ -> rnd.Next (0,upper_bound))
;;

(* benchmarking *)
let benchmark  f = 

    let stopWatch = System.Diagnostics.Stopwatch.StartNew() in

    let ls = f (mk 10000 10000);

    stopWatch.Stop();
    stopWatch.Elapsed.TotalMilliseconds
    