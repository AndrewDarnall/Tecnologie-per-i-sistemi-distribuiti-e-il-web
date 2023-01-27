<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class BookController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $db = \App\Models\Book::all();
        return view('BookStore.bookList',compact('db'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('BookStore.addBook');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $db = new \App\Models\Book;
        $db->title = request('title');
        $db->author = request('author');
        $db->book_seeries = request('book_seeries');
        $db->rating = request('rating');
        $db->price = request('price');
        $db->save();
        return redirect('/books');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $db = \App\Models\Book::findOrFail($id);
        return view('BookStore.details', compact('db'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $db = \App\Models\Book::findOrFail($id);
        return view('BookStore.updatebook', compact('db'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $db = \App\Models\Book::findOrFail($id);
        $db->title = request('title');
        $db->author = request('author');
        $db->book_seeries = request('book_seeries');
        $db->rating = request('rating');
        $db->price = request('price');
        $db->save();
        return redirect('/books');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        \App\Models\Book::findOrFail($id)->delete();
        return redirect('/books');
    }
}
