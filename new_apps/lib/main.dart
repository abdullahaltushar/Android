import 'package:flutter/material.dart';
import './result.dart';
import './quiz.dart';
void main(){
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  var _questionIndex=0;
  final _questions= const[
    {'questionText': 'What\'s is your favorite colour?',
      'answers':[
  {'text':'Black','score':4},
  {'text':'Red','score':3},
  {'text':'Green','score':2},
  {'text':'Yellow','score':1}
  ],},
    {'questionText': 'what\'s is your favorite animal?',
      'answers':[
        {'text': 'Tiger','score':1},
        {'text':'Cat','score':2},
        {'text':'Dog','score':3},
        {'text':'Lion','score':4}
      ],},
    {'questionText': 'what\'s is your favorite city?',
      'answers':[
        {'text': 'Dhaka','score':1},
        {'text':'Chittagong','score':2},
        {'text':'Cox\'bazar','score':3},
        {'text':'Bandorbon','score':4}
      ],}

  ];
  var _totalScore=0;
  void _restartQuiz(){
    setState((){
      _totalScore=0;
      _questionIndex=0;
    });
  }
  void _answerQuestion(int score){
    _totalScore=_totalScore + score;
    setState((){
      _questionIndex=_questionIndex+1;
    });
    print(_questionIndex);
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Quiz"),
          backgroundColor: Colors.deepPurple,
        ),
        body: _questionIndex< _questions.length ?
            Quiz(
              answerQuestion: _answerQuestion,
              questions: _questions,
              questionIndex: _questionIndex,

            ): Result(_totalScore,_restartQuiz)

      )

    );
  }
}

