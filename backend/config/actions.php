<?php

return [
    \charly\application\action\HomeAction::class => DI\autowire(),
    \charly\application\action\GetListCompetence::class => DI\autowire(),
    \charly\application\action\PostBesoin::class => DI\autowire(),
    \charly\application\action\PostSalarie::class => DI\autowire(),
    \charly\application\action\GetSalaries::class => DI\autowire(),
    \charly\application\action\PostSignIn::class => DI\autowire(),
];
