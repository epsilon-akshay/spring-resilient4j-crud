package main

import (
	"fmt"
	"net/http"
)

func main() {
	http.HandleFunc("/", getRoot)

	err := http.ListenAndServe(":9090", nil)
	panic(err)
}
func getRoot(w http.ResponseWriter, r *http.Request) {
	fmt.Printf("got / request\n %+v", r)
	w.WriteHeader(500)
}
