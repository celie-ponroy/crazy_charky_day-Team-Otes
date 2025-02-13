<?php

namespace charly\application\action;
use charly\core\service\interfaces\CompetenceServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class CreateCompetence extends AbstractAction
{
    protected CompetenceServiceInterface $competenceService;

    public function __construct(CompetenceServiceInterface $competenceService)
    {
        $this->competenceService = $competenceService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $data = $rq->getParsedBody();

            if (!isset($data['libelle']) || !isset($data['description'])) {
                return $this->respondWithError($rs, 'Body manquant', 400);
            }

            $this->competenceService->addCompetence($data['libelle'], $data['description']);

            $responseData = [
                'success' => true,
                'message' => 'Competence enregistrée avec succès'
            ];

            $rs->getBody()->write(json_encode($responseData));
            return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
        } catch (\Exception $e) {
            return $this->respondWithError($rs, $e->getMessage(), 500);
        }
    }

    private function respondWithError(ResponseInterface $response, string $message, int $status): ResponseInterface
    {
        $responseData = [
            'status' => $status,
            'error' => $message
        ];

        $response->getBody()->write(json_encode($responseData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

        return $response->withHeader('Content-Type', 'application/json')->withStatus($status);
    }
}