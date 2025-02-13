<?php

namespace charly\application\action;

abstract class AbstractAction
{
    protected Logger $loger;
    public function __construct(LoggerInterface $l)
    {
        $this->loger = $l;
    }

    /**
     * @param array<int,mixed> $args
     */
    abstract public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface ;


}