grammar MarineShell;

program: statement+ EOF;

statement
    : declaration ';'
    | command ';'
    ;

declaration
    : IDENTIFIER ':=' expression
    ;

expression
    : 'new' objectType '(' paramList? ')'
    | IDENTIFIER
    ;

objectType
    : 'Satellite'
    | 'Beacon'          // Using Beacon instead of Balise to match existing code
    ;

command
    : IDENTIFIER '.' method
    ;

method
    : 'start'
    | 'stop'
    | 'setSpeed' '(' NUMBER ')'
    | 'setPattern' '(' pattern ')'
    | 'setColor' '(' color ')'
    ;

paramList
    : param (',' param)*
    ;

param
    : 'height' '=' NUMBER      // For Satellite
    | 'speed' '=' NUMBER       // For Satellite
    | 'pattern' '=' pattern    // For both
    | 'depth' '=' NUMBER       // For Beacon
    | 'color' '=' color        // For both
    ;

pattern
    : '#horizontal'
    | '#sinusoidal'
    | '#stationary'
    ;

color
    : '#red'
    | '#blue'
    | '#green'
    | '#yellow'
    ;

// Lexer rules
IDENTIFIER: [a-zA-Z][a-zA-Z0-9_]*;
NUMBER: '-'? [0-9]+ ('.' [0-9]+)?;
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;