// Author: Oliver Gratton
// for tree sort, see BST file in "trees" folder

(*  insertion sort  *)

let rec insert_sort = function
    | [] -> []
    | x :: l -> insert x (insert_sort l)
  and insert elem = function
    | [] -> [elem]
    | x :: l -> if elem < x then elem :: x :: l
                else x :: insert elem l
;;

(*  merge sort  *)

let rec halve = function (* not used cos I didn't write it, nor do I understand it *)
    | []
    | [_] as t1 -> t1, []
    | h::t ->
        let t1,t2 = halve t in
            h::t2, t1
;;

let split list = (* noob alternative to halve *)
    let rec split' list h1 h2 =
        match list with
        |x::y::xys -> split' xys (x::h1) (y::h2)
        |x::[]     -> (x::h1),h2
        |[]        -> h1,h2
    in split' list [] []
;;

let rec merge l1 l2 = 
    match l1,l2 with
    |list,[]
    |[],list         -> list (* don't need a case for 2 empties as these cover it *)
    |(x::l1),(y::l2) -> if (x < y) then x :: (merge l1 (y::l2)) else y :: (merge (x::l1) l2)

let rec merge_sort = function
    | []    -> []
    | x::[] -> [x]
    |list   -> let ls = split list in 
                merge (merge_sort (fst ls)) (merge_sort (snd ls))

(*  quick sort  *)

let part p list =
    let rec poop p list (a,b) = 
        match list with
        |[]             -> a,b
        |x::xs when x<p -> poop p xs (a@[x],b)
        |x::xs          -> poop p xs (a,b@[x])
    in poop p list ([],[])

let rec quick_sort = function
    |[]     -> []
    |x::[]  -> [x]
    |x::xs  -> let ls = part x xs in
                (quick_sort (fst ls)) @ (x::quick_sort (snd ls))



(* quicksort using better partition *)

let rec partition p = function
    |[] -> [],[]
    |x::xs -> let (ps,nps) = partition p xs in
                if p x then (x::ps,nps) else (ps, x::nps)


let rec quicksort = function
    | [] -> []
    | x::xs -> let smaller, larger = partition (fun y -> y < x) xs
               in quicksort smaller @ (x::quicksort larger)



(* make a random list to be sorted *)
let mk size upper_bound =
    let rnd = System.Random()
    List.init size (fun _ -> rnd.Next (0,upper_bound))
;;


(* benchmarking *)
let benchmark  (f:int list -> int list) = 

    let stopWatch = System.Diagnostics.Stopwatch.StartNew() in

    let ls = f (mk 10000 10000);
    //merge_sort ( quicksort (mk 10000 10000))
    //quicksort ( merge_sort (mk 10000 10000)) (* showcasing quicksort's worst case *)

    stopWatch.Stop();
    stopWatch.Elapsed.TotalMilliseconds