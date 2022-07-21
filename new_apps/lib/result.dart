import 'package:flutter/material.dart';
class Result extends StatelessWidget {
  final int resultscore;
  final Function Restart;
  Result(this.resultscore,this.Restart);
  String get resultPhase{
    String textResult;
    if(resultscore>=12){
      textResult="You are awesome";
    }
    else if(resultscore<12 && resultscore>=10){
      textResult="You are briliant";
    }
    else if(resultscore<10 && resultscore>=8){
      textResult="You are well";
    }
    else{
      textResult="You are not well";
    }
    return textResult;

  }
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(resultPhase,style: TextStyle(
            fontSize: 28,
            fontWeight: FontWeight.bold,
            color: Colors.red,
          ),
            textAlign: TextAlign.center,
          ),
          FlatButton(onPressed: (){Restart();}, child: Text("Restart Quiz",style: TextStyle(
            backgroundColor: Colors.black,
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
          textAlign: TextAlign.center,))
        ],
      ),
    );
  }
}
